  function getAdmisssionDetails() {
    var AdmissionData = new Object();
    AdmissionData.AdmissionNumber=jQuery('#AdmissionNumber').val();
    AdmissionData.DateOfJoining=jQuery('#DateOfJoining').val();
    AdmissionData.StudentClassId=jQuery('#StudentClass').val();
    AdmissionData.Remarks=jQuery('#Remarks').val();
    return AdmissionData;
  }

  function getPersonalDetails() {
    var PersonalDetails = new Object();
    PersonalDetails.FirstName=jQuery('#FirstName').val();
    PersonalDetails.MiddleName=jQuery('#MiddleName').val();
    PersonalDetails.LastName=jQuery('#LastName').val();
    PersonalDetails.Gender=jQuery('#Gender').val();
    PersonalDetails.DateOfBirth=jQuery('#DateOfBirth').val();
    PersonalDetails.Religion=jQuery('#Religion').val();
    PersonalDetails.Caste=jQuery('#Caste').val();
    PersonalDetails.Nationality=jQuery('#Nationality').val();
    PersonalDetails.MotherTongue=jQuery('#MotherTongue').val();
    PersonalDetails.MobileNumber=jQuery('#MobileNumber').val();
    PersonalDetails.BloodGroup=jQuery('#BloodGroup').val();
    PersonalDetails.PermanentAddress=jQuery('#PermanentAddress').val();
    PersonalDetails.CorrespondenceAddress=jQuery('#CorrespondenceAddress').val();
    PersonalDetails.IdentificationMarks=jQuery('#IdentificationMarks').val();
    return PersonalDetails;
  }

  function getFamilyMemberDetails(datatable) {
    var FamilyMemberDetails = new Array();
    var rows = datatable.fnGetNodes();

    for(var index=0; index<rows.length; index++) {
      var row = datatable.fnGetData(rows[index]);
      var FamilyMemberDetail = new Object();
      if (row[0] <= 0) {
        FamilyMemberDetail.FamilyMemberId=0;
      } else {
        FamilyMemberDetail.FamilyMemberId=row[0];
      }
      FamilyMemberDetail.RelationshipCode=row[1];
      FamilyMemberDetail.RelationshipName=row[2];
      FamilyMemberDetail.FamilyMemberName=row[3];
      FamilyMemberDetail.Occupation=row[4];
      FamilyMemberDetail.MobileNumber=row[5];
      FamilyMemberDetail.EmailID=row[6];
      FamilyMemberDetail.AvailSMS=row[7];
      FamilyMemberDetail.AvailEmail=row[8];
      FamilyMemberDetails[FamilyMemberDetails.length] = FamilyMemberDetail;
    }
    return FamilyMemberDetails;
  }

  function getStudentData() {
    var FamilyMembersDetail = new Array();
    var ParentDetails = getFamilyMemberDetails($('#ParentsInfoTable').dataTable());
    var SiblingDetails = getFamilyMemberDetails($('#SiblingsInfoTable').dataTable());

    for (var index = 0; index < ParentDetails.length; index++) {
        FamilyMembersDetail[FamilyMembersDetail.length] = ParentDetails[index];
    }
    for (var index = 0; index < SiblingDetails.length; index++) {
        FamilyMembersDetail[FamilyMembersDetail.length] = SiblingDetails[index];
    }

    var StudentData = new Object();
    StudentData.PersonalDetails=getPersonalDetails();
    StudentData.FamilyMembersDetail=FamilyMembersDetail;
    StudentData.AdmissionData=getAdmisssionDetails();
    StudentData.DocumentDetails=getDocumentDetails();
    StudentData.StudentRegistrationType=jQuery('#StudentRegistrationType').val();
    StudentData.SecureTokenId=jQuery('#SecureTokenId').val();
    return StudentData;
  }

  function clearDataTable(dataTable) {
    var oSettings = dataTable.fnSettings();
    var iTotalRecords = oSettings.fnRecordsTotal();
    for (i=0;i<=iTotalRecords;i++) {
      dataTable.fnDeleteRow(0,null,true);
    }
  }