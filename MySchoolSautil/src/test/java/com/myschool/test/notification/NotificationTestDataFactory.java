package com.myschool.test.notification;

import java.util.ArrayList;
import java.util.List;

import com.myschool.apar.constants.Ledger;
import com.myschool.apar.dto.CashPayment;
import com.myschool.apar.dto.ChequePayment;
import com.myschool.apar.dto.CreditCardPayment;
import com.myschool.apar.dto.EmployeePay;
import com.myschool.apar.dto.FeeSummary;
import com.myschool.apar.dto.FeeTransaction;
import com.myschool.apar.dto.PayComponent;
import com.myschool.apar.dto.Payment;
import com.myschool.attendance.constants.LeaveStatus;
import com.myschool.attendance.dto.LeaveDto;
import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.branch.dto.RegionDto;
import com.myschool.branch.dto.StateDto;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.common.constants.DocumentApplicability;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.dto.Person;
import com.myschool.common.dto.PersonalDetailsDto;
import com.myschool.common.dto.Relationship;
import com.myschool.common.exception.ValidationException;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.employee.dto.EmployeeContact;
import com.myschool.employee.dto.EmployeeDocument;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeEducation;
import com.myschool.employee.dto.EmployeeExperience;
import com.myschool.employee.dto.EmployeePromotion;
import com.myschool.employee.dto.EmployeeSubjectDto;
import com.myschool.employee.dto.EmploymentStatus;
import com.myschool.exam.dto.ExamDto;
import com.myschool.exam.dto.StudentExamDto;
import com.myschool.exam.dto.StudentInExamDto;
import com.myschool.exam.dto.SubjectExamDto;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationMode;
import com.myschool.notification.constants.NotificationType;
import com.myschool.school.dto.SchoolDto;
import com.myschool.student.dto.AdmissionStatus;
import com.myschool.student.dto.StudentDocument;
import com.myschool.student.dto.StudentDto;

/**
 * A factory for creating NotificationTestData objects.
 */
public class NotificationTestDataFactory {

    /**
     * Gets the to person.
     * 
     * @return the to person
     */
    public static Person getToPerson() {
        Person person = new Person();
        person.setEmailId("abc@xyz.com");
        person.setFirstName("First Name");
        person.setLastName("Last Name");
        person.setMiddleName("Middle Name");
        person.setMobileNumber("XXX XXX XXXX");
        return person;
    }

    /**
     * Gets the object.
     * 
     * @param notificationEndPoint the notification end point
     * @param notificationMode the notification mode
     * @param notificationType the notification type
     * @return the object
     */
    public static Object getObject(
            NotificationEndPoint notificationEndPoint,
            NotificationMode notificationMode, NotificationType notificationType) {
        Object object = null;
        try {
            if (notificationEndPoint == NotificationEndPoint.EMPLOYEE) {
                object = getEmployeeInformation(notificationType);
            } else if (notificationEndPoint == NotificationEndPoint.NOTICE_BOARD) {
                //object = getNoticeBoard();
            } else if (notificationEndPoint == NotificationEndPoint.STUDENT) {
                object = getStudentInformation(notificationType);
            }
        } catch (Exception exception) {
            // Nothing to do!!! its just preparing test data
            exception.printStackTrace();
        }
        return object;
    }

    /**
     * Gets the employee information.
     * 
     * @param notificationType the notification type
     * @return the employee information
     * @throws ValidationException the validation exception
     */
    private static Object getEmployeeInformation(
            NotificationType notificationType) throws ValidationException {
        Object object = null;
        if (notificationType == NotificationType.MONTHLY_ATTENDANCE_REPORT) {/*
            AttendanceDto attendance = null;//getMonthlyAttendance();
            if (attendance instanceof AttendanceMonth) {
                AttendanceMonth monthAttendance = (AttendanceMonth) attendance;
                List<AttendanceDay> dayAttendances = monthAttendance.getDayAttendances();

                if (dayAttendances != null && !dayAttendances.isEmpty()) {
                    for (int index = 0; index < dayAttendances.size(); index++) {
                        AttendanceDay dayAttendance = dayAttendances.get(index);
                        if (dayAttendance != null) {
                            AttendanceCode attendanceCode = dayAttendance.getAttendanceCode();
                            if (attendanceCode != AttendanceCode.GENERAL_HOLIDAY
                                    && attendanceCode != AttendanceCode.DECLARED_HOLIDAY
                                    && dayAttendance.getDay().getDate()%2 == 0) {
                                dayAttendance.setAttendanceCode(AttendanceCode.PRESENT);
                            }
                        }
                        if (dayAttendance != null
                                && !dayAttendance.isDeclaredHoliday()
                                && !dayAttendance.isGeneralHoliday()
                                //&& !dayAttendance.isHoliday()
                                && dayAttendance.getDay().getDate()%2 == 0) {
                            dayAttendance.setPresent(true);
                        }
                    }
                }
                //AttendanceAssembler.updateAttendanceCounts(dayAttendances, monthAttendance);
            }
            object = new Object[] {getEmployee(), attendance};
        */} else if (notificationType == NotificationType.YEARLY_ATTENDANCE_REPORT) {/*
            AttendanceDto attendance = null;//getYearlyAttendance();
            if (attendance instanceof AttendanceYear) {
                AttendanceYear yearAttendance = (AttendanceYear) attendance;
                List<AttendanceMonth> monthAttendances = yearAttendance.getMonthAttendances();
                if (monthAttendances != null && !monthAttendances.isEmpty()) {
                    for (AttendanceMonth monthAttendance : monthAttendances) {
                        List<AttendanceDay> dayAttendances = monthAttendance.getDayAttendances();
                        if (dayAttendances != null && !dayAttendances.isEmpty()) {
                            for (int index = 0; index < dayAttendances.size(); index++) {
                                AttendanceDay dayAttendance = dayAttendances.get(index);
                                if (dayAttendance != null) {
                                    AttendanceCode attendanceCode = dayAttendance.getAttendanceCode();
                                    if (attendanceCode != AttendanceCode.GENERAL_HOLIDAY
                                            && attendanceCode != AttendanceCode.DECLARED_HOLIDAY
                                            && dayAttendance.getDay().getDate()%2 == 0) {
                                        dayAttendance.setAttendanceCode(AttendanceCode.PRESENT);
                                    }
                                }
                                if (dayAttendance != null
                                        && !dayAttendance.isDeclaredHoliday()
                                        && !dayAttendance.isGeneralHoliday()
                                        //&& !dayAttendance.isHoliday()
                                        && dayAttendance.getDay().getDate()%2 == 0) {
                                    dayAttendance.setPresent(true);
                                }
                            }
                        }
                        //AttendanceAssembler.updateAttendanceCounts(dayAttendances, monthAttendance);
                    }
                }
                //AttendanceAssembler.updateAttendanceCounts(monthAttendances, yearAttendance);
            }
            object = new Object[] {getEmployee(), attendance};
        */} else if (notificationType == NotificationType.LEAVE_NOTIFICATION) {
            object = new Object[] {getEmployee(), getLeave()};
        } else if (notificationType == NotificationType.PAYSLIP) {
            object = new Object[] {getEmployeePay()};
        } else {
            object = getEmployee();
        }
        return object;
    }

    /**
     * Gets the employee pay.
     * 
     * @return the employee pay
     * @throws ValidationException the validation exception
     */
    private static EmployeePay getEmployeePay() throws ValidationException {
        EmployeePay employeePay = new EmployeePay();
        employeePay.setEmployee(getEmployee());
        employeePay.setPayComponents(getPayComponents());
        employeePay.setPayment(getChequePayment(40000));
        //employeePay.setAttendance(getMonthlyAttendance());
        return employeePay;
    }

    /**
     * Gets the pay components.
     * 
     * @return the pay components
     */
    private static List<PayComponent> getPayComponents() {
        List<PayComponent> payComponents = new ArrayList<PayComponent>();
        // Basic Pay
        PayComponent basicPay = new PayComponent();
        basicPay.setAmount(12000);
        basicPay.setComponentName("Basic Pay");
        basicPay.setLedger(Ledger.CR);
        payComponents.add(basicPay);
        // Dearness Allowance
        PayComponent da = new PayComponent();
        da.setAmount(200);
        da.setComponentName("Dearness Allowance");
        da.setLedger(Ledger.CR);
        payComponents.add(da);
        // House Rent Allowance
        PayComponent hra = new PayComponent();
        hra.setAmount(4000);
        hra.setComponentName("House Rent Allowance");
        hra.setLedger(Ledger.CR);
        payComponents.add(hra);
        // Transport Allowance
        PayComponent ta = new PayComponent();
        ta.setAmount(500);
        ta.setComponentName("Transport Allowance");
        ta.setLedger(Ledger.CR);
        payComponents.add(ta);
        // Medical Reimbursement
        PayComponent mr = new PayComponent();
        mr.setAmount(100);
        mr.setComponentName("Medical Reimbursement");
        mr.setLedger(Ledger.CR);
        payComponents.add(mr);
        // Leave Encashment
        PayComponent le = new PayComponent();
        le.setAmount(1000);
        le.setComponentName("Leave Encashment");
        le.setLedger(Ledger.CR);
        payComponents.add(le);
        // Other Allowance
        PayComponent oa = new PayComponent();
        oa.setAmount(250);
        oa.setComponentName("Other Allowance");
        oa.setLedger(Ledger.CR);
        payComponents.add(oa);
        // Employees Provident Fund
        PayComponent epf = new PayComponent();
        epf.setAmount(600);
        epf.setComponentName("Employees Provident Fund");
        epf.setLedger(Ledger.DR);
        payComponents.add(epf);
        // Profession Tax
        PayComponent professionalTax = new PayComponent();
        professionalTax.setAmount(200);
        professionalTax.setComponentName("Profession Tax");
        professionalTax.setLedger(Ledger.DR);
        payComponents.add(professionalTax);
        // TDS
        PayComponent tds = new PayComponent();
        tds.setAmount(450);
        tds.setComponentName("TDS");
        tds.setLedger(Ledger.DR);
        payComponents.add(tds);
        return payComponents;
    }

    /**
     * Gets the student information.
     * 
     * @param notificationType the notification type
     * @return the student information
     * @throws ValidationException the validation exception
     */
    private static Object getStudentInformation(
            NotificationType notificationType) throws ValidationException {
        Object object = null;
        if (notificationType == NotificationType.MONTHLY_ATTENDANCE_REPORT) {/*
            StudentAttendanceDto studentAttendanceDto = new StudentAttendanceDto();
            studentAttendanceDto.setStudent(getStudent());
            AttendanceDto attendance = null;//getMonthlyAttendance();
            if (attendance instanceof AttendanceMonth) {
                AttendanceMonth monthAttendance = (AttendanceMonth) attendance;
                List<AttendanceDay> dayAttendances = monthAttendance.getDayAttendances();

                if (dayAttendances != null && !dayAttendances.isEmpty()) {
                    for (int index = 0; index < dayAttendances.size(); index++) {
                        AttendanceDay dayAttendance = dayAttendances.get(index);
                        if (dayAttendance != null) {
                            AttendanceCode attendanceCode = dayAttendance.getAttendanceCode();
                            if (attendanceCode != AttendanceCode.GENERAL_HOLIDAY
                                    && attendanceCode != AttendanceCode.DECLARED_HOLIDAY
                                    && dayAttendance.getDay().getDate()%2 == 0) {
                                dayAttendance.setAttendanceCode(AttendanceCode.PRESENT);
                            }
                        }
                        if (dayAttendance != null
                                && !dayAttendance.isDeclaredHoliday()
                                && !dayAttendance.isGeneralHoliday()
                                //&& !dayAttendance.isHoliday()
                                && dayAttendance.getDay().getDate()%2 == 0) {
                            dayAttendance.setPresent(true);
                        }
                    }
                }
                //AttendanceAssembler.updateAttendanceCounts(dayAttendances, monthAttendance);
            }
            studentAttendanceDto.setAttendance(attendance);
            object = studentAttendanceDto;
        */} else if (notificationType == NotificationType.YEARLY_ATTENDANCE_REPORT) {/*
            StudentAttendanceDto studentAttendanceDto = new StudentAttendanceDto();
            studentAttendanceDto.setStudent(getStudent());
            AttendanceDto attendance = null;//getYearlyAttendance();
            if (attendance instanceof AttendanceYear) {
                AttendanceYear yearAttendance = (AttendanceYear) attendance;
                List<AttendanceMonth> monthAttendances = yearAttendance.getMonthAttendances();
                if (monthAttendances != null && !monthAttendances.isEmpty()) {
                    for (AttendanceMonth monthAttendance : monthAttendances) {
                        List<AttendanceDay> dayAttendances = monthAttendance.getDayAttendances();
                        if (dayAttendances != null && !dayAttendances.isEmpty()) {
                            for (int index = 0; index < dayAttendances.size(); index++) {
                                AttendanceDay dayAttendance = dayAttendances.get(index);
                                if (dayAttendance != null) {
                                    AttendanceCode attendanceCode = dayAttendance.getAttendanceCode();
                                    if (attendanceCode != AttendanceCode.GENERAL_HOLIDAY
                                            && attendanceCode != AttendanceCode.DECLARED_HOLIDAY
                                            && dayAttendance.getDay().getDate()%2 == 0) {
                                        dayAttendance.setAttendanceCode(AttendanceCode.PRESENT);
                                    }
                                }
                                if (dayAttendance != null
                                        && !dayAttendance.isDeclaredHoliday()
                                        && !dayAttendance.isGeneralHoliday()
                                        //&& !dayAttendance.isHoliday()
                                        && dayAttendance.getDay().getDate()%2 == 0) {
                                    dayAttendance.setPresent(true);
                                }
                            }
                        }
                        //AttendanceAssembler.updateAttendanceCounts(dayAttendances, monthAttendance);
                    }
                }
                //AttendanceAssembler.updateAttendanceCounts(monthAttendances, yearAttendance);
            }
            studentAttendanceDto.setAttendance(attendance);
            object = studentAttendanceDto;
        */} else if (notificationType == NotificationType.EXAM_RESULT) {
            StudentDto student = getStudent();
            StudentInExamDto studentInExam = new StudentInExamDto();
            studentInExam.setGrade("A+");
            studentInExam.setPercentage(80);
            studentInExam.setStudent(student);
            studentInExam.setStudentExams(getStudentExams());
            studentInExam.setTotalMarks(498);
            object = new Object[]{studentInExam, getExam()};
        } else if (notificationType == NotificationType.LEAVE_NOTIFICATION) {
            object = new Object[] {getStudent(), getLeave()};
        }  else if (notificationType == NotificationType.FEE_SUMMARY) {
            object = new Object[] {getStudent(), getFeeSummary()};
        } else {
            object = getStudent();
        }
        return object;
    }

    /**
     * Gets the fee summary.
     * 
     * @return the fee summary
     */
    private static FeeSummary getFeeSummary() {
        FeeSummary feeSummary = new FeeSummary();
        List<FeeTransaction> feeTransactions = getFeeTransactions();
        feeSummary.setFeeTransactions(feeTransactions);
        updateFeeSummary(feeSummary, feeTransactions);
        return feeSummary;
    }

    /**
     * Update fee summary.
     * 
     * @param feeSummary the fee summary
     * @param feeTransactions the fee transactions
     */
    private static void updateFeeSummary(FeeSummary feeSummary,
            List<FeeTransaction> feeTransactions) {
        double totalFeePaid = 0;
        double totalFeeToPay = 0;
        if (feeTransactions != null && !feeTransactions.isEmpty()) {
            for (FeeTransaction feeTransaction : feeTransactions) {
                double amount = feeTransaction.getAmount();
                Ledger ledger = feeTransaction.getLedger();
                if (ledger == Ledger.CR) {
                    totalFeeToPay = totalFeeToPay + amount;
                } else if (ledger == Ledger.DR) {
                    totalFeePaid = totalFeePaid + amount;
                }
            }
        }
        totalFeePaid = -1 * totalFeePaid;
        feeSummary.setTotalFeeToPay(totalFeeToPay);
        feeSummary.setTotalFeePaid(totalFeePaid);
        feeSummary.setTotalRemainingFeeToPay(totalFeeToPay + totalFeePaid);
    }

    /**
     * Gets the fee transactions.
     * 
     * @return the fee transactions
     */
    private static List<FeeTransaction> getFeeTransactions() {
        List<FeeTransaction> feeTransactions = new ArrayList<FeeTransaction>();
        feeTransactions.add(getFeeTransaction(Ledger.CR, 500, "Library Fee"));
        feeTransactions.add(getFeeTransaction(Ledger.DR, 500, CashPayment.class, "Library Fee"));
        feeTransactions.add(getFeeTransaction(Ledger.CR, 1000, "Exam Fee"));
        feeTransactions.add(getFeeTransaction(Ledger.DR, 1000, ChequePayment.class, "Exam Fee"));
        feeTransactions.add(getFeeTransaction(Ledger.CR, 2000, "Annual Fee"));
        feeTransactions.add(getFeeTransaction(Ledger.DR, 1500, CreditCardPayment.class, "Annual Fee"));
        return feeTransactions;
    }

    /**
     * Gets the fee transaction.
     * 
     * @param ledger the ledger
     * @param amount the amount
     * @param particulars the particulars
     * @return the fee transaction
     */
    private static FeeTransaction getFeeTransaction(Ledger ledger, double amount,
            String particulars) {
        return getFeeTransaction(ledger, amount, null, particulars);
    }

    /**
     * Gets the fee transaction.
     * 
     * @param ledger the ledger
     * @param amount the amount
     * @param class1 the class1
     * @param particulars the particulars
     * @return the fee transaction
     */
    private static FeeTransaction getFeeTransaction(Ledger ledger,
            double amount, Class<? extends Payment> class1, String particulars) {
        FeeTransaction feeTransaction = new FeeTransaction();
        feeTransaction.setAmount(amount);
        feeTransaction.setLedger(ledger);
        feeTransaction.setParticulars(particulars);
        if (ledger == Ledger.CR) {
            feeTransaction.setDueDate("2013-09-21");
            feeTransaction.setTransactionDate("2013-09-21");
        } else if (ledger == Ledger.DR) {
            feeTransaction.setDueDate("2013-09-30");
            feeTransaction.setTransactionDate("2013-09-30");
            if (class1 == CashPayment.class) {
                feeTransaction.setPayment(getCashPayment(amount));
            } else if (class1 == ChequePayment.class) {
                feeTransaction.setPayment(getChequePayment(amount));
            } else if (class1 == CreditCardPayment.class) {
                feeTransaction.setPayment(getCreditCardPayment(amount));
            }
        }
        return feeTransaction;
    }

    /**
     * Gets the cash payment.
     * 
     * @param amount the amount
     * @return the cash payment
     */
    private static CashPayment getCashPayment(double amount) {
        CashPayment cashPayment = new CashPayment();
        cashPayment.setAmountPaid(amount);
        cashPayment.setPaymentDate("2013-10-21");
        return cashPayment;
    }

    /**
     * Gets the cheque payment.
     * 
     * @param amount the amount
     * @return the cheque payment
     */
    private static ChequePayment getChequePayment(double amount) {
        ChequePayment chequePayment = new ChequePayment();
        chequePayment.setAmountPaid(amount);
        chequePayment.setCheckRealizationDate("2013-10-21");
        chequePayment.setChequeNumber("20131021");
        chequePayment.setIssuingBanker("ABC Bank");
        chequePayment.setPaymentDate("2013-10-16");
        return chequePayment;
    }

    /**
     * Gets the credit card payment.
     * 
     * @param amount the amount
     * @return the credit card payment
     */
    private static CreditCardPayment getCreditCardPayment(double amount) {
        CreditCardPayment creditCardPayment = new CreditCardPayment();
        creditCardPayment.setAmountPaid(amount);
        creditCardPayment.setCardNumber("4065 XXXX XXXX 9005");
        creditCardPayment.setPaymentDate("2013-10-21");
        return creditCardPayment;
    }

    /**
     * Gets the leave.
     * 
     * @return the leave
     */
    private static LeaveDto getLeave() {
        LeaveDto leave = new LeaveDto();
        leave.setEndDate("2013-10-23");
        leave.setNumberOfDays(3);
        leave.setComments("Not Well");
        leave.setStartDate("2013-10-21");
        leave.setType("CL");
        leave.setLeaveStatus(LeaveStatus.WAITING_APPROVAL);
        return leave;
    }

    /**
     * Gets the exam.
     * 
     * @return the exam
     */
    private static ExamDto getExam() {
        ExamDto exam = new ExamDto();
        exam.setExamCompleted(true);
        exam.setExamDate("2013-10-19");
        exam.setExamId(1);
        exam.setExamName("Annual Exam");
        exam.setRegisteredClass(getRegisteredClassDto());
        exam.setSubjectExams(getSubjectExams());
        return exam;
    }

    /**
     * Gets the subject exams.
     * 
     * @return the subject exams
     */
    private static List<SubjectExamDto> getSubjectExams() {
        List<SubjectExamDto> subjectExams = new ArrayList<SubjectExamDto>();
        for (int index = 0; index < 5; index++) {
            subjectExams.add(getSubjectExam(index));
        }
        return subjectExams;
    }

    /**
     * Gets the student exams.
     * 
     * @return the student exams
     */
    private static List<StudentExamDto> getStudentExams() {
        List<StudentExamDto> studentExams = new ArrayList<StudentExamDto>();
        for (int index = 0; index < 5; index++) {
            StudentExamDto studentExam = new StudentExamDto();
            studentExam.setObtainedMarks((index+1) * 20);
            studentExam.setStudentExamId(1);
            SubjectExamDto subjectExam = getSubjectExam(index);
            studentExam.setSubjectExam(subjectExam);
            studentExam.setSubjectExamId(index+1);
            studentExams.add(studentExam);
        }
        return studentExams;
    }

    /**
     * Gets the subject exam.
     * 
     * @param index the index
     * @return the subject exam
     */
    private static SubjectExamDto getSubjectExam(int index) {
        SubjectExamDto subjectExam = new SubjectExamDto();
        subjectExam.setMaximumMarks(100);
        SubjectDto subject = new SubjectDto();
        subject.setSubjectId(index+1);
        subject.setSubjectName("Subject " + (index + 1));
        RegisteredSubjectDto registeredSubject = new RegisteredSubjectDto();
        registeredSubject.setSubjectId(index+1);
        registeredSubject.setSubject(subject);
        subjectExam.setRegisteredSubject(registeredSubject);
        subjectExam.setSubjectExamId(index+1);
        return subjectExam;
    }

    /**
     * Gets the yearly attendance.
     * 
     * @return the yearly attendance
     * @throws ValidationException the validation exception
     *//*
    private static AttendanceDto getYearlyAttendance() throws ValidationException {
        return AttendanceAssembler.getYearAttendance(Calendar.getInstance(), null);
    }
    
    *//**
     * Gets the monthly attendance.
     * 
     * @return the monthly attendance
     * @throws ValidationException the validation exception
     *//*
    private static AttendanceDto getMonthlyAttendance() throws ValidationException {
        Calendar calendar = Calendar.getInstance();
        return AttendanceAssembler.getMonthAttendance(calendar, null, calendar.get(Calendar.MONTH));
    }*/

    /**
     * Gets the employee.
     * 
     * @return the employee
     */
    private static EmployeeDto getEmployee() {
        EmployeeDto employee = new EmployeeDto();
        employee.setEmployeeId(1);
        employee.setEmployeeNumber("1");
        employee.setFirstName("First Name");
        employee.setMiddleName("Middle Name");
        employee.setLastName("Last Name");
        employee.setGender("M");
        employee.setDateOfBirth("1981-01-12");
        employee.setBloodGroup("AB+");
        employee.setNationality("Indian");
        employee.setMaritalStatus("Married");
        employee.setWeddingDay("2012-05-02");
        employee.setEmploymentStartDate("2007-11-01");
        employee.setEmploymentEndDate("2014-11-01");
        // load reporting to
        EmployeeDto reportingTo = new EmployeeDto();
        reportingTo.setFirstName("Reporting First Name");
        reportingTo.setFirstName("Reporting Middle Name");
        reportingTo.setFirstName("Reporting Last Name");
        reportingTo.setEmployeeId(2);
        employee.setReportingTo(reportingTo);
        employee.setRemarks("Remarks");

        employee.setEmployedAtBranch(getBranch());
        employee.setDesignation(getDesignation());
        employee.setEmployeeContact(getEmployeeContact());
        employee.setEmploymentStatus(getEmploymentStatus());
        employee.setEmployeeDocuments(getEmployeeDocuments());
        employee.setEmployeeEducations(getEmployeeEducations());
        employee.setEmployeeExperiences(getEmployeeExperiences());
        employee.setEmployeePromotions(getEmployeePromotions());
        employee.setEmployeeSubjects(getEmployeeSubjects());
        return reportingTo;
    }

    /**
     * Gets the employee educations.
     * 
     * @return the employee educations
     */
    private static List<EmployeeEducation> getEmployeeEducations() {
        List<EmployeeEducation> employeeEducations = new ArrayList<EmployeeEducation>();
        EmployeeEducation employeeEducation = null;
        for (int index = 1; index <= 3; index++) {
            employeeEducation = new EmployeeEducation();
            employeeEducation.setCollege("College " + index);
            employeeEducation.setDegree("Degree " + index);
            employeeEducation.setEducationId(index);
            employeeEducation.setPercentage(30 * index);
            employeeEducation.setSpecialization("Specialization " + index);
            employeeEducation.setUniversity("University " + index);
            employeeEducation.setYearOfGraduation(2012);
            employeeEducations.add(employeeEducation);
        }
        return employeeEducations;
    }

    /**
     * Gets the employee experiences.
     * 
     * @return the employee experiences
     */
    private static List<EmployeeExperience> getEmployeeExperiences() {
        List<EmployeeExperience> employeeExperiences = new ArrayList<EmployeeExperience>();
        EmployeeExperience employeeExperience = null;
        for (int index = 1; index <= 3; index++) {
            employeeExperience = new EmployeeExperience();
            employeeExperience.setEmployer("Employer " + index);
            employeeExperience.setExperieceInMonth(index * 12);
            employeeExperience.setExperienceId(index);
            employeeExperience.setFromDate("2011-10-11");
            employeeExperience.setToDate("2012-10-11");
            employeeExperience.setJobTitle("Job Title " + (index+1));
            employeeExperiences.add(employeeExperience);
        }
        return employeeExperiences;
    }

    /**
     * Gets the employee promotions.
     * 
     * @return the employee promotions
     */
    private static List<EmployeePromotion> getEmployeePromotions() {
        List<EmployeePromotion> employeePromotions = new ArrayList<EmployeePromotion>();
        EmployeePromotion employeePromotion = null;
        DesignationDto priorDesignation = null;
        DesignationDto currentDesignation = null;
        for (int index = 1; index <= 3; index++) {
            employeePromotion = new EmployeePromotion();
            currentDesignation = new DesignationDto();
            priorDesignation = new DesignationDto();

            currentDesignation.setDesignationId(index);
            currentDesignation.setDesignation("Designation " + (index));
            employeePromotion.setCurrentDesignation(currentDesignation);

            priorDesignation.setDesignationId(index-1);
            priorDesignation.setDesignation("Designation " + (index-1));
            employeePromotion.setPriorDesignation(priorDesignation);

            employeePromotion.setEffectiveFrom("2012-10-11");
            employeePromotion.setPromotionId(index);

            employeePromotions.add(employeePromotion);
        }
        return employeePromotions;
    }

    /**
     * Gets the employee subjects.
     * 
     * @return the employee subjects
     */
    private static List<EmployeeSubjectDto> getEmployeeSubjects() {
        List<EmployeeSubjectDto> employeeSubjects = new ArrayList<EmployeeSubjectDto>();
        SubjectDto subject = null;
        EmployeeSubjectDto employeeSubject = null;

        for (int index = 1; index <= 5; index++) {
            employeeSubject = new EmployeeSubjectDto();
            employeeSubject.setEmployeeSubjectId(index);
            RegisteredSubjectDto registeredSubject = new RegisteredSubjectDto();
            employeeSubject.setRegisteredSubject(registeredSubject);
            subject = new SubjectDto();
            subject.setSubjectId(index);
            subject.setSubjectName("Subject " + index);
            registeredSubject.setSubjectId(index);
            registeredSubject.setSubject(subject);
            employeeSubjects.add(employeeSubject);
        }
        return employeeSubjects;
    }

    /**
     * Gets the employee contact.
     * 
     * @return the employee contact
     */
    private static EmployeeContact getEmployeeContact() {
        EmployeeContact employeeContact = new EmployeeContact();
        employeeContact.setEmergencyContactNumber("1234567890");
        employeeContact.setEmergencyContactRelationship(getRelationship("FATHER"));
        employeeContact.setOfficeDeskExtension("4321");
        employeeContact.setOfficeDeskPhoneNumber("1234567890");
        employeeContact.setOfficeEmailId("name@company.com");
        employeeContact.setOfficeMobileNumber("9876543210");
        employeeContact.setPermanentAddress("Permanent Address");
        employeeContact.setPersonalEmailId("personal@isp.com");
        employeeContact.setPersonalMobileNumber("1234567890");
        employeeContact.setPresentAddress("Present Address");
        return employeeContact;
    }

    /**
     * Gets the employment status.
     * 
     * @return the employment status
     */
    private static EmploymentStatus getEmploymentStatus() {
        EmploymentStatus employmentStatus = new EmploymentStatus();
        employmentStatus.setStatusId(1);
        employmentStatus.setDescription("Active");
        return employmentStatus;
    }

    /**
     * Gets the employee documents.
     * 
     * @return the employee documents
     */
    private static List<EmployeeDocument> getEmployeeDocuments() {
        List<EmployeeDocument> employeeDocuments = new ArrayList<EmployeeDocument>();
        EmployeeDocument employeeDocument1 = new EmployeeDocument();
        employeeDocument1.setDocument(getDocument(1));
        employeeDocument1.setDocumentExpiryDate("2020-11-12");
        employeeDocument1.setDocumentIssuedBy("Govt of India");
        employeeDocument1.setDocumentNumber("1234567890");

        EmployeeDocument employeeDocument2 = new EmployeeDocument();
        employeeDocument2.setDocument(getDocument(2));
        employeeDocument2.setDocumentExpiryDate("2020-12-12");
        employeeDocument2.setDocumentIssuedBy("Govt of India");
        employeeDocument2.setDocumentNumber("1234567890");

        employeeDocuments.add(employeeDocument1);
        employeeDocuments.add(employeeDocument2);
        return employeeDocuments;
    }

    /**
     * Gets the designation.
     * 
     * @return the designation
     */
    private static DesignationDto getDesignation() {
        DesignationDto designation = new DesignationDto();
        designation.setDesignation("Designation");
        designation.setDesignationId(1);
        return designation;
    }

    /**
     * Gets the student.
     * 
     * @return the student
     */
    private static StudentDto getStudent() {
        StudentDto student = new StudentDto();
        AdmissionStatus admissionStatus = new AdmissionStatus();
        admissionStatus.setStatusId(1);
        admissionStatus.setDescription("Active");
        student.setAdmissionStatus(admissionStatus);
        student.setAdmissionNumber("1");
        student.setAwarenessTrainings(null);
        student.setDateOfJoining("2013-10-10");
        student.setDocumentsSubmitted(getStudentDocuments());
        student.setFamilyMembers(getFamilyMembers());
        student.setPersonalDetails(getPersonalDetails());
        student.setRegisteredClassDto(getRegisteredClassDto());
        student.setRemarks("Remarks");
        student.setStudentId(1);
        return student;
    }

    /**
     * Gets the family members.
     * 
     * @return the family members
     */
    private static List<FamilyMemberDto> getFamilyMembers() {
        List<FamilyMemberDto> familyMembers = new ArrayList<FamilyMemberDto>();
        int index=1;
        familyMembers.add(getFamilyMember(index++, "Father"));
        familyMembers.add(getFamilyMember(index++, "Mother"));
        familyMembers.add(getFamilyMember(index++, "Guardian"));
        familyMembers.add(getFamilyMember(index++, "Brother"));
        familyMembers.add(getFamilyMember(index++, "Sister"));
        return familyMembers;
    }

    /**
     * Gets the documents.
     * 
     * @return the documents
     */
    private static List<StudentDocument> getStudentDocuments() {
        List<StudentDocument> studentDocuments = new ArrayList<StudentDocument>();
        StudentDocument studentDocument1 = new StudentDocument();
        studentDocument1.setDocument(getDocument(1));
        studentDocument1.setDocumentExpiryDate("2020-11-12");
        studentDocument1.setDocumentIssuedBy("Govt of India");
        studentDocument1.setDocumentNumber("1234567890");

        StudentDocument studentDocument2 = new StudentDocument();
        studentDocument2.setDocument(getDocument(2));
        studentDocument2.setDocumentExpiryDate("2020-12-12");
        studentDocument2.setDocumentIssuedBy("Govt of India");
        studentDocument2.setDocumentNumber("1234567890");

        studentDocuments.add(studentDocument1);
        studentDocuments.add(studentDocument2);
        return studentDocuments;
    }

    /**
     * Gets the document.
     * 
     * @param index the index
     * @return the document
     */
    private static DocumentDto getDocument(int index) {
        DocumentDto document = new DocumentDto();
        document.setDocumentId(index);
        document.setName("Document " + index);
        document.setApplicabilityForEmployee(DocumentApplicability.MANDATORY);
        document.setApplicabilityForStudent(DocumentApplicability.MANDATORY);
        document.setDescription("Document " + index);
        return document;
    }

    /**
     * Gets the registered class dto.
     * 
     * @return the registered class dto
     */
    private static RegisteredClassDto getRegisteredClassDto() {
        RegisteredClassDto registeredClassDto = new RegisteredClassDto();
        registeredClassDto.setSchool(getSchool());
        registeredClassDto.setClassDto(getClassDto());
        registeredClassDto.setClassId(1);
        registeredClassDto.setMedium(getMedium());
        registeredClassDto.setSchool(getSchool());
        registeredClassDto.setSection(getSection());
        return registeredClassDto;
    }

    /**
     * Gets the personal details.
     * 
     * @return the personal details
     */
    private static PersonalDetailsDto getPersonalDetails() {
        PersonalDetailsDto personalDetails = new PersonalDetailsDto();
        personalDetails.setBloodGroup("AB+");
        personalDetails.setCaste("Caste");
        personalDetails.setCorrespondenceAddress("CorrespondenceAddress");
        personalDetails.setDateOfBirth("1970-01-01");
        personalDetails.setFirstName("First Name");
        personalDetails.setGender("M");
        personalDetails.setIdentificationMarks("Identification Marks");
        personalDetails.setLastName("Last Name");
        personalDetails.setMiddleName("Middle Name");
        personalDetails.setMobileNumber("123 456 7890");
        personalDetails.setMotherTongue("Mother Tongue");
        personalDetails.setNationality("Nationality");
        personalDetails.setPermanentAddress("Permanent Address");
        personalDetails.setReligion("Religion");
        return personalDetails;
    }

    /**
     * Gets the class dto.
     * 
     * @return the class dto
     */
    private static ClassDto getClassDto() {
        ClassDto classDto = new ClassDto();
        classDto.setClassId(1);
        classDto.setClassName("Class Name");
        classDto.setPromotionOrder(100);
        return classDto;
    }

    /**
     * Gets the medium.
     * 
     * @return the medium
     */
    private static MediumDto getMedium() {
        MediumDto medium = new MediumDto();
        medium.setDescription("Description");
        medium.setMediumId(1);
        return medium;
    }

    /**
     * Gets the school.
     * 
     * @return the school
     */
    private static SchoolDto getSchool() {
        SchoolDto school = new SchoolDto();
        school.setAddress("Address");
        school.setBranch(getBranch());
        school.setDivision(getDivision());
        school.setEmailId("school@myschoolims.in");
        school.setFaxNumber("123 456 7890");
        school.setMapUrl("http://www.google.co.in");
        school.setMobileNumber("123 456 7890");
        school.setPrimaryPhoneNumber("123 456 7890");
        school.setSchoolId(1);
        school.setSchoolName("School Name");
        school.setSecondaryPhoneNumber("123 456 7890");
        return school;
    }

    /**
     * Gets the division.
     * 
     * @return the division
     */
    private static DivisionDto getDivision() {
        DivisionDto division = new DivisionDto();
        division.setDescription("Description");
        division.setDivisionCode("DC");
        division.setDivisionId(1);
        return division;
    }

    /**
     * Gets the branch.
     * 
     * @return the branch
     */
    private static BranchDto getBranch() {
        BranchDto branch = new BranchDto();
        branch.setAddress("Address");
        branch.setBranchCode("BC");
        branch.setBranchId(1);
        branch.setDescription("Description");
        branch.setEmailId("branch@myschoolims.in");
        branch.setMapUrl("http://www.google.co.in");
        branch.setPhoneNumber("123 456 7890");
        branch.setRegion(getRegion());
        return branch;
    }

    /**
     * Gets the region.
     * 
     * @return the region
     */
    private static RegionDto getRegion() {
        RegionDto region = new RegionDto();
        region.setRegionId(1);
        region.setRegionName("Region Name");
        region.setState(getState());
        return region;
    }

    /**
     * Gets the state.
     * 
     * @return the state
     */
    private static StateDto getState() {
        StateDto state = new StateDto();
        state.setStateId(1);
        state.setStateName("State Name");
        return state;
    }

    /**
     * Gets the section.
     * 
     * @return the section
     */
    private static SectionDto getSection() {
        SectionDto section = new SectionDto();
        section.setSectionId(1);
        section.setSectionName("Section Name");
        return section;
    }

    /**
     * Gets the family member.
     * 
     * @param familyMemberId the family member id
     * @param relationshipCode the relationship code
     * @return the family member
     */
    private static FamilyMemberDto getFamilyMember(int familyMemberId, String relationshipCode) {
        FamilyMemberDto familyMember = new FamilyMemberDto();
        familyMember.setAvailEmail(true);
        familyMember.setAvailSMS(true);
        familyMember.setEmailId("familymember" + familyMemberId + "@myschoolims.in");
        familyMember.setFamilyMemberId(1);
        familyMember.setMobileNumber(String.valueOf(1111111111 * familyMemberId));
        familyMember.setName("Family Member " + familyMemberId);
        familyMember.setOccupation("Occupation " + familyMemberId);
        familyMember.setRelationship(getRelationship(relationshipCode));
        return familyMember;
    }

    /**
     * Gets the relationship.
     * 
     * @param relationshipName the relationship name
     * @return the relationship
     */
    private static Relationship getRelationship(String relationshipName) {
        Relationship relationship = new Relationship();
        if (relationshipName != null) {
            relationship.setCode(String.valueOf(relationshipName.charAt(0)));
            relationship.setName(relationshipName);
        }
        return null;
    }

}