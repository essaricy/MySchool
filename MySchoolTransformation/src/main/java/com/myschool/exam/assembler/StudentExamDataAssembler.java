package com.myschool.exam.assembler;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.common.dto.PersonalDetailsDto;
import com.myschool.exam.dto.ExamDto;
import com.myschool.exam.dto.ExamGradeDto;
import com.myschool.exam.dto.StudentExamDto;
import com.myschool.exam.dto.StudentExamsSummaryDto;
import com.myschool.exam.dto.StudentInExamDto;
import com.myschool.exam.dto.SubjectExamDto;
import com.myschool.graph.dto.AxisDto;
import com.myschool.graph.dto.LineChartDto;
import com.myschool.student.assembler.StudentDataAssembler;
import com.myschool.student.dto.StudentDto;

/**
 * The Class StudentExamDataAssembler.
 */
public class StudentExamDataAssembler {

    /**
     * Creates the student exam.
     *
     * @param resultSet the result set
     * @return the student exam dto
     * @throws SQLException the sQL exception
     */
    public static StudentInExamDto createStudentInExam(ResultSet resultSet) throws SQLException {
        StudentInExamDto studentExam = new StudentInExamDto();

        PersonalDetailsDto personalDetails = new PersonalDetailsDto();
        personalDetails.setFirstName(resultSet.getString("FIRST_NAME"));
        personalDetails.setMiddleName(resultSet.getString("MIDDLE_NAME"));
        personalDetails.setLastName(resultSet.getString("LAST_NAME"));
        personalDetails.setMobileNumber(resultSet.getString("MOBILE_NUMBER"));

        StudentDto student = new StudentDto();
        student.setStudentId(resultSet.getInt("STUDENT_ID"));
        student.setAdmissionNumber(resultSet.getString("ADMISSION_NUMBER"));
        student.setPersonalDetails(personalDetails);
        studentExam.setStudent(student);

        updateStudentSubjectExam(resultSet, studentExam);
        return studentExam;
    }

    /**
     * Update student subject exam.
     *
     * @param resultSet the result set
     * @param studentInExam the student in exam
     * @throws SQLException the sQL exception
     */
    public static void updateStudentSubjectExam(ResultSet resultSet,
            StudentInExamDto studentInExam) throws SQLException {

        int studentExamId = 0;
        StudentExamDto studentExam = null;
        List<StudentExamDto> studentExams = null;

        if (studentInExam != null) {
            studentExams = studentInExam.getStudentExams();
            if (studentExams == null) {
                studentExams = new ArrayList<StudentExamDto>();
            }
            studentExamId = resultSet.getInt("STUDENT_EXAM_ID");
            if (studentExamId != 0) {
                studentExam = new StudentExamDto();
                studentExam.setObtainedMarks(resultSet.getInt("OBTAINED_MARKS"));
                studentExam.setStudentExamId(studentExamId);
                studentExam.setSubjectExamId(resultSet.getInt("SUBJECT_EXAM_ID"));
                studentExams.add(studentExam);
                studentInExam.setStudentExams(studentExams);
            }
        }
    }

    /**
     * Gets the grade.
     *
     * @param percentage the percentage
     * @param examGrades the exam grades
     * @return the grade
     */
    private static String getGrade(double percentage, List<ExamGradeDto> examGrades) {
        int lowest = 0;
        int qualifyingPercentage = 0;
        String grade = null;

        if (examGrades != null && !examGrades.isEmpty()) {
            for (ExamGradeDto examGrade : examGrades) {
                if (examGrade != null) {
                    qualifyingPercentage = examGrade.getQualifyingPercentage();
                    if ((percentage - qualifyingPercentage >= 0)
                            && (percentage - lowest >= 0)
                            && percentage - qualifyingPercentage < percentage - lowest) {
                        lowest = qualifyingPercentage;
                    }
                }
            }
            for (ExamGradeDto examGrade : examGrades) {
                if (examGrade != null) {
                    if (lowest == examGrade.getQualifyingPercentage()) {
                        grade = examGrade.getGradeName();
                    }
                }
            }
        }
        return grade;
    }

    /**
     * Creates the student exam.
     *
     * @param resultSet the result set
     * @return the student exam dto
     * @throws SQLException the sQL exception
     */
    public static StudentExamDto createStudentExam(ResultSet resultSet) throws SQLException {
        StudentExamDto studentExam = new StudentExamDto();
        studentExam.setStudentExamId(resultSet.getInt("STUDENT_EXAM_ID"));
        StudentDto student = new StudentDto();
        student.setStudentId(resultSet.getInt("STUDENT_ID"));
        studentExam.setStudent(student);
        studentExam.setSubjectExamId(resultSet.getInt("SUBJECT_EXAM_ID"));
        studentExam.setObtainedMarks(resultSet.getInt("OBTAINED_MARKS"));
        SubjectExamDto subjectExam = new SubjectExamDto();
        subjectExam.setSubjectExamId(resultSet.getInt("SUBJECT_EXAM_ID"));
        subjectExam.setMaximumMarks(resultSet.getInt("MAXIMUM_MARKS"));
        studentExam.setSubjectExam(subjectExam);
        return studentExam;
    }

    /**
     * Creates the student in exam for exam.
     *
     * @param resultSet the result set
     * @return the student exam dto
     * @throws SQLException the sQL exception
     */
    public static StudentExamDto createStudentInExamForExam(
            ResultSet resultSet) throws SQLException {
        StudentExamDto studentExam = new StudentExamDto();

        SubjectExamDto subjectExam = new SubjectExamDto();
        RegisteredSubjectDto registeredSubject = new RegisteredSubjectDto();
        SubjectDto subject = new SubjectDto();

        subject.setSubjectId(resultSet.getInt("SUBJECT_ID"));
        subject.setSubjectName(resultSet.getString("SUBJECT_NAME"));

        registeredSubject.setSubject(subject);
        registeredSubject.setSubjectId(resultSet.getInt("OPERATING_SUBJECT_ID"));
        subjectExam.setRegisteredSubject(registeredSubject);
        subjectExam.setSubjectExamId(resultSet.getInt("SUBJECT_EXAM_ID"));
        subjectExam.setMaximumMarks(resultSet.getInt("MAXIMUM_MARKS"));

        studentExam.setSubjectExam(subjectExam);
        studentExam.setObtainedMarks(resultSet.getInt("OBTAINED_MARKS"));
        return studentExam;
    }

    /**
     * Gets the total marks.
     *
     * @param studentExams the student exams
     * @return the total marks
     */
    private static int getTotalMarks(List<StudentExamDto> studentExams) {
        int totalMarks = 0;
        SubjectExamDto subjectExam = null;
        if (studentExams != null) {
            for (StudentExamDto studentExam : studentExams) {
                if (studentExam != null) {
                    subjectExam = studentExam.getSubjectExam();
                    if (subjectExam != null) {
                        totalMarks = totalMarks + subjectExam.getMaximumMarks();
                    }
                }
            }
        }
        return totalMarks;
    }

    /**
     * Creates the student in exam.
     *
     * @param studentExams the student exams
     * @param examGrades the exam grades
     * @return the student in exam dto
     */
    public static StudentInExamDto createStudentInExam(
            List<StudentExamDto> studentExams, List<ExamGradeDto> examGrades) {
        int obtainedMarks = 0;
        int studentTotalMarks = 0;
        double percentage = 0;
        String grade = null;
        StudentInExamDto studentInExam = null;

        if (studentExams != null) {
            percentage = 0;
            studentTotalMarks = 0;
            grade = null;

            studentInExam = new StudentInExamDto();
            int totalMarks = getTotalMarks(studentExams);
            for (StudentExamDto studentExam : studentExams) {
                if (studentExam != null) {
                    obtainedMarks = studentExam.getObtainedMarks();
                    studentTotalMarks = studentTotalMarks + obtainedMarks;
                }
            }
            percentage = ((double)studentTotalMarks * 100)/(double)totalMarks;
            grade = getGrade(percentage, examGrades);

            studentInExam.setTotalMarks(studentTotalMarks);
            BigDecimal bigDecimal = new BigDecimal(percentage);
            bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            studentInExam.setPercentage(bigDecimal.doubleValue());
            studentInExam.setGrade(grade);
            studentInExam.setStudentExams(studentExams);
        }
        return studentInExam;
    }

    /**
     * Creates the student in exam.
     *
     * @param studentsInExam the students in exam
     * @return the jSON array
     */
    public static JSONArray createStudentInExam(
            List<StudentInExamDto> studentsInExam) {
        JSONArray jsonArray = null;
        if (studentsInExam != null) {
            jsonArray = new JSONArray();
            for (int index = 0; index < studentsInExam.size(); index++) {
                jsonArray.put(createStudentInExam(studentsInExam.get(index)));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the student in exam.
     *
     * @param studentInExamDto the student in exam dto
     * @return the jSON object
     */
    private static JSONObject createStudentInExam(StudentInExamDto studentInExamDto) {
        JSONObject jsonObject = null;
        if (studentInExamDto != null) {
            jsonObject = new JSONObject();
            jsonObject.put("Percentage", studentInExamDto.getPercentage());
            jsonObject.put("TotalMarks", studentInExamDto.getTotalMarks());
            jsonObject.put("Grade", studentInExamDto.getGrade());
            jsonObject.put("Student", StudentDataAssembler.create(studentInExamDto.getStudent()));
            jsonObject.put("StudentExams", createStudentExams(studentInExamDto.getStudentExams()));
        }
        return jsonObject;
    }

    /**
     * Creates the student exams.
     *
     * @param studentExams the student exams
     * @return the jSON array
     */
    private static JSONArray createStudentExams(List<StudentExamDto> studentExams) {
        JSONArray jsonArray = null;
        if (studentExams != null) {
            jsonArray = new JSONArray();
            for (int index = 0; index < studentExams.size(); index++) {
                jsonArray.put(createStudentExam(studentExams.get(index)));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the student exam.
     *
     * @param studentExamDto the student exam dto
     * @return the jSON object
     */
    private static JSONObject createStudentExam(StudentExamDto studentExamDto) {
        JSONObject jsonObject = null;
        if (studentExamDto != null) {
            jsonObject = new JSONObject();
            jsonObject.put("ObtainedMarks", studentExamDto.getObtainedMarks());
            jsonObject.put("StudentExamId", studentExamDto.getStudentExamId());
            jsonObject.put("SubjectExamId", studentExamDto.getSubjectExamId());
            //jsonObject.put("Percentage", studentExamDto.getPercentage());
            jsonObject.put("Student", StudentDataAssembler.create(studentExamDto.getStudent()));
            jsonObject.put("SubjectsInExam", SubjectExamsDataAssembler.createSubjectsInExam(studentExamDto.getSubjectExam()));
        }
        return jsonObject;
    }

    /**
     * Creates the student exam.
     * 
     * @param studentExams the student exams
     * @return the jSON array
     */
    private static JSONArray createStudentExam(List<StudentExamDto> studentExams) {
        JSONArray jsonArray = null;
        if (studentExams != null && !studentExams.isEmpty()) {
            jsonArray = new JSONArray();
            for (StudentExamDto studentExam : studentExams) {
                jsonArray.put(createStudentExam(studentExam));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     * 
     * @param studentsInExam the students in exam
     * @return the jSON array
     */
    public static JSONArray create(List<StudentInExamDto> studentsInExam) {
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;
        if (studentsInExam != null && !studentsInExam.isEmpty()) {
            jsonArray = new JSONArray();
            for (StudentInExamDto studentInExam : studentsInExam) {
                StudentDto student = studentInExam.getStudent();
                PersonalDetailsDto personalDetails = student.getPersonalDetails();
                jsonObject = new JSONObject();
                jsonObject.put("AdmissionNumber", student.getAdmissionNumber());
                jsonObject.put("FirstName", personalDetails.getFirstName());
                jsonObject.put("MiddleName", personalDetails.getMiddleName());
                jsonObject.put("LastName", personalDetails.getLastName());

                jsonObject.put("TotalMarks", studentInExam.getTotalMarks());
                jsonObject.put("Percentage", studentInExam.getPercentage());
                jsonObject.put("Grade", studentInExam.getGrade());
                jsonArray.put(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * Creates the student exam summary.
     * 
     * @param studentExamsSummary the student exams summary
     * @return the jSON object
     */
    public static JSONObject createStudentExamSummary(
            StudentExamsSummaryDto studentExamsSummary) {
        JSONObject jsonObject = null;
        JSONArray subjectMarksJSONObject = null;
        JSONArray subjectMarksJSONArray = null;
        List<SubjectExamDto> subjectExams = null;
        List<StudentExamDto> studentExams = null;
        Map<SubjectExamDto, List<StudentExamDto>> studentSubjectExamMarks = null;
        if (studentExamsSummary != null) {
            StudentDto student = studentExamsSummary.getStudent();
            if (student != null) {
                jsonObject = new JSONObject();
                jsonObject.put("Student", StudentDataAssembler.create(student));
                List<ExamDto> exams = studentExamsSummary.getExams();
                if (exams != null && !exams.isEmpty()) {
                    jsonObject.put("Exams", ExamDataAssembler.create(exams));

                    subjectExams = studentExamsSummary.getSubjectExams();
                    studentSubjectExamMarks = studentExamsSummary.getStudentSubjectExamMarks();
                    if (subjectExams != null && !subjectExams.isEmpty()
                            && studentSubjectExamMarks != null && !studentSubjectExamMarks.isEmpty()) {
                        subjectMarksJSONArray = new JSONArray();
                        jsonObject.put("SubjectsInExams", SubjectExamsDataAssembler.createSubjectsInExams(subjectExams));
                        for (SubjectExamDto subjectExam : subjectExams) {
                            studentExams = studentSubjectExamMarks.get(subjectExam);
                            if (studentExams != null && !studentExams.isEmpty()) {
                                subjectMarksJSONObject = new JSONArray();
                                subjectMarksJSONObject.put(createStudentExam(studentExams));
                                subjectMarksJSONArray.put(subjectMarksJSONObject);
                                //subjectMarksJSONArray.put(studentSubjectExamMarks);
                            }
                        }
                        jsonObject.put("SubjectMarks", subjectMarksJSONArray);
                    }
                }
            }
        }
        return jsonObject;
    }

    /**
     * Creates the.
     * 
     * @param studentExamsSummary the student exams summary
     * @return the line chart dto
     */
    public static LineChartDto create(StudentExamsSummaryDto studentExamsSummary) {
        LineChartDto lineChart = null;
        if (studentExamsSummary != null) {
            lineChart = new LineChartDto();
            lineChart.setXAxis(createXAxis(studentExamsSummary.getExams()));
            lineChart.setSeriesNames(createSeriesNames(studentExamsSummary.getSubjectExams()));
            lineChart.setLineSeries(createLineSeries(studentExamsSummary.getSubjectExams(), studentExamsSummary.getStudentSubjectExamMarks()));
        }
        return lineChart;
    }

    private static List<String> createSeriesNames(
            List<SubjectExamDto> subjectExams) {
        List<String> seriesNames = null;
        SubjectDto subject = null;
        RegisteredSubjectDto registeredSubject = null;
        if (subjectExams != null && !subjectExams.isEmpty()) {
            seriesNames = new ArrayList<String>();
            for (SubjectExamDto subjectExam : subjectExams) {
                if (subjectExam != null) {
                    registeredSubject = subjectExam.getRegisteredSubject();
                    if (registeredSubject != null) {
                        subject = registeredSubject.getSubject();
                        if (subject != null) {
                            seriesNames.add(subject.getSubjectName());
                        }
                    }
                }
            }
        }
        return seriesNames;
    }

    /**
     * Creates the line series.
     * 
     * @param subjectExams the subject exams
     * @param studentSubjectExamMarks the student subject exam marks
     * @return the list
     */
    private static List<List<BigDecimal>> createLineSeries(
            List<SubjectExamDto> subjectExams, Map<SubjectExamDto, List<StudentExamDto>> studentSubjectExamMarks) {
        List<BigDecimal> lineSeries = null;
        List<List<BigDecimal>> lineSeriesData = null;
        if (subjectExams != null && !subjectExams.isEmpty()
                && studentSubjectExamMarks != null && !studentSubjectExamMarks.isEmpty()) {
            lineSeriesData = new ArrayList<List<BigDecimal>>();
            for (SubjectExamDto subjectExam : subjectExams) {
                if (subjectExam != null && subjectExam.getMaximumMarks() != 0) {
                    lineSeries = new ArrayList<BigDecimal>();
                    List<StudentExamDto> studentExams = studentSubjectExamMarks.get(subjectExam);
                    if (studentExams != null && !studentExams.isEmpty()) {
                        for (StudentExamDto studentExam : studentExams) {
                            if (studentExam != null) {
                                int obtainedMarks = studentExam.getObtainedMarks();
                                int maximumMarks = studentExam.getSubjectExam().getMaximumMarks();
                                double percentage = ((double)obtainedMarks * 100)/(double)maximumMarks;
                                BigDecimal bigDecimal = new BigDecimal(percentage);
                                bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
                                lineSeries.add(bigDecimal);
                            }
                        }
                    }
                    lineSeriesData.add(lineSeries);
                }
            }
        }
        return lineSeriesData;
    }

    /**
     * Creates the x axis.
     * 
     * @param exams the exams
     * @return the axis dto
     */
    private static AxisDto createXAxis(List<ExamDto> exams) {
        AxisDto axisDto = null;
        List<String> markers = null;
        if (exams != null && !exams.isEmpty()) {
            axisDto = new AxisDto();
            markers = new ArrayList<String>();
            axisDto.setMarkers(markers);
            for (ExamDto exam : exams) {
                if (exam != null) {
                    markers.add(exam.getExamName());
                }
            }
        }
        return axisDto;
    }

}
