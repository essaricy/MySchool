package com.myschool.academic.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.academic.dto.AcademicYearClosureDto;
import com.myschool.academic.dto.AyeProcessCriteriaDto;
import com.myschool.application.dao.ProfileDao;
import com.myschool.application.domain.ProfileManager;
import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.common.constants.CacheKeyConstants;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.exam.domain.ExamGradeManager;
import com.myschool.infra.cache.agent.InMemoryCacheAgent;

/**
 * The Class AyeProcessManager.
 */
@Component
public class AyeProcessManager {

    /** The in memory cache agent. */
    @Autowired
    private InMemoryCacheAgent inMemoryCacheAgent;

    /** The profile manager. */
    @Autowired
    private ProfileManager profileManager;

    /** The academic manager. */
    @Autowired
    private AcademicManager academicManager;

    /** The exam grade manager. */
    @Autowired
    private ExamGradeManager examGradeManager;

    /** The profile dao. */
    @Autowired
    private ProfileDao profileDao;

    /**
     * Gets the academic year closure.
     * 
     * @return the academic year closure
     * @throws DataException the data exception
     */
    public AcademicYearClosureDto getAcademicYearClosure() throws DataException {
        AcademicYearClosureDto academicYearClosure = new AcademicYearClosureDto();
        academicYearClosure.setOrganizationProfile(profileManager.getOrganizationProfile());
        academicYearClosure.setMySchoolProfile(profileManager.getMyschoolProfile());
        academicYearClosure.setCurrentAcademic(academicManager.getCurrentAcademic());
        academicYearClosure.setNextAcademic(academicManager.getNextAcademic());
        academicYearClosure.setExamGrades(examGradeManager.getGrades());
        return academicYearClosure;
    }

    /**
     * Initiate academic year closure.
     * 
     * @param ayeProcessCriteria the aye process criteria
     * @return the list
     * @throws DataException the data exception
     */
    public List<ResultDto> initiateAcademicYearClosure(
            AyeProcessCriteriaDto ayeProcessCriteria) throws DataException {
        List<ResultDto> canStartAcademicYearEnd = canStartAcademicYearEnd();
        if (canStartAcademicYearEnd == null || canStartAcademicYearEnd.isEmpty()) {
            // TODO can initiate academic year closure process
            // Update the AYE status
            // Send message to the middleware to start corresponding sautil program.
            ResultDto resultDto = new ResultDto();
            resultDto.setSuccessful(ResultDto.SUCCESS);
            resultDto.setStatusMessage("Academic Year Closure process has been successfully started.");
            List<ResultDto> closureStatus = new ArrayList<ResultDto>();
            closureStatus.add(resultDto);
            return closureStatus;
        } else {
            return canStartAcademicYearEnd;
        }
    }

    /**
     * Can start academic year end.
     * 
     * @return the list
     */
    private List<ResultDto> canStartAcademicYearEnd() {
        // Check if academic year closure is in progress or not
        // Check if next academic year is available or not.
        // Check if exam grades are defined or not.
        // check if all the exams are frozen are not.
        // Check if all the student marks are entered or not.
        // Check if all reference attendances are closed or not
        // Check if all student attendances are frozen or not.
        return null;
    }

    /**
     * Update.
     * 
     * @param ayeProgressStatus the aye progress status
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(String ayeProgressStatus) throws DataException {
        try {
            MySchoolProfileDto myschoolProfile = profileManager.getMyschoolProfile();
            boolean existingAyeInProgress = myschoolProfile.isAyeInProgress();
            boolean requestedAyeInProgress = ConversionUtil.toBoolean(ayeProgressStatus);
            if (existingAyeInProgress) {
                if (requestedAyeInProgress) {
                    throw new ValidationException("Academic Year Closure is already in progress.");
                } else {
                    profileDao.updateAyeProgress(ayeProgressStatus);
                    myschoolProfile.setAyeInProgress(requestedAyeInProgress);
                    //inMemoryCacheAgent.putEntry(CacheKeyConstants.MY_SCHOOL_PROFILE, myschoolProfile);
                }
            } else {
                if (requestedAyeInProgress) {
                    profileDao.updateAyeProgress(ayeProgressStatus);
                    myschoolProfile.setAyeInProgress(requestedAyeInProgress);
                    inMemoryCacheAgent.putEntry(CacheKeyConstants.MY_SCHOOL_PROFILE, myschoolProfile);
                } else {
                    // Ignore the request
                    return false;
                }
            }
            return true;
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Process aye.
     *
     * @param ayeProcessCriteria the aye process criteria
     * @return the result dto
     * @throws DataException the data exception
     *//*
    public ResultDto processAye(AyeProcessCriteriaDto ayeProcessCriteria) throws DataException {
        ResultDto result = null;
        try {
            result = new ResultDto();
            AcademicDto nextAcademic = academicDao.getNextAcademic();
            // check if all exams are frozen or not.
            List<ExamDto> examsInCurrentAcademic = examDao.getExamsInCurrentAcademic();
            if (examsInCurrentAcademic != null) {
                for (ExamDto exam : examsInCurrentAcademic) {
                    if (exam != null && exam.isExamCompleted()) {
                        throw new DataException("Exam (" + exam.getExamName() + ") is still open.");
                    }
                }
            }
            processBranchesAye(nextAcademic.getAcademicYearName());
            ayeProcessDao.setNextAcademicYear(nextAcademic.getAcademicYearName());
            result.setSuccessful(ResultDto.SUCCESS);
            result.setStatusMessage("Academic Year End process has been completed successfully.");
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return result;
    }

    *//**
     * Process branches aye.
     *
     * @param nextAcademicYear the next academic year
     * @throws DaoException the dao exception
     *//*
    private void processBranchesAye(String nextAcademicYear) throws DaoException {
        int branchId;
        List<BranchDto> branches = branchDao.getAll();
        if (branches != null) {
            for (BranchDto branch : branches) {
                if (branch != null) {
                    branchId = branch.getBranchId();
                    processSchoolsAye(branchId, nextAcademicYear);
                }
            }
        }
    }

    *//**
     * Process schools aye.
     *
     * @param branchId the branch id
     * @param nextAcademicYear the next academic year
     * @throws DaoException the dao exception
     *//*
    private void processSchoolsAye(int branchId, String nextAcademicYear) throws DaoException {
        int schoolId;
        List<SchoolDto> schoolsInBranch = schoolDao.getByBranch(branchId);
        if (schoolsInBranch != null) {
            for (SchoolDto school : schoolsInBranch) {
                if (school != null) {
                    schoolId = school.getSchoolId();
                    processClassesAye(schoolId, nextAcademicYear);
                }
            }
        }
    }

    *//**
     * Process classes aye.
     *
     * @param schoolId the school id
     * @param nextAcademicYear the next academic year
     * @throws DaoException the dao exception
     *//*
    private void processClassesAye(int schoolId, String nextAcademicYear) throws DaoException {
        int classId;
        List<RegisteredClassDto> classesInSchool;
        RegisteredClassDto nextClass;
        classesInSchool = classDao.getBySchool(schoolId);
        if (classesInSchool != null) {
            for (RegisteredClassDto registeredClass : classesInSchool) {
                if (registeredClass != null) {
                    classId = registeredClass.getClassId();
                    nextClass = classDao.getNextClass(schoolId, classId);
                    if (nextClass != null) {
                        processStudentsAye(classId, nextClass, nextAcademicYear);
                    }
                }
            }
        }
    }

    *//**
     * Process students aye.
     *
     * @param classId the class id
     * @param nextClass the next class
     * @param nextAcademicYear the next academic year
     * @throws DaoException the dao exception
     *//*
    private void processStudentsAye(int classId, RegisteredClassDto nextClass, String nextAcademicYear) throws DaoException {
        int studentId;
        List<StudentDto> studentsInClass;
        studentsInClass = studentDao.getCurrentAyStudents(classId);
        if (studentsInClass != null) {
            for (StudentDto student : studentsInClass) {
                boolean promote = true;
                if (student != null) {
                    studentId = student.getStudentId();
                    // check if the student is eligible for promotion
                    if (promote) {
                        if (studentDao.promoteStudent(studentId, nextClass.getClassId(), nextAcademicYear)) {
                        } else {
                        }
                    }
                }
            }
        }
    }*/

}
