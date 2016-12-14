package com.myschool.web.publicdata.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.branch.service.BranchService;
import com.myschool.organization.service.OrganizationService;
import com.myschool.school.service.SchoolService;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.publicdata.constants.PublicDataViewNames;

@Controller
@RequestMapping("public")
public class PublicDataController {

    /** The Constant LOGGER. */
    //private static final Logger LOGGER = Logger.getLogger(PublicDataController.class);

    @Autowired
    private OrganizationService organizationService;

    /** The branch service. */
    @Autowired
    private BranchService branchService;

    /** The school service. */
    @Autowired
    private SchoolService schoolService;

    @RequestMapping(value = "dashboard")
    public ModelAndView dashboard(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ pubic/dashboard.htm");
        return ViewDelegationController.delegateWholePageView(request, PublicDataViewNames.PUBLIC_DASHBOARD);
    }

    @RequestMapping(value = "aboutOrganization")
    public ModelAndView aboutOrganization(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ pubic/aboutOrganization.htm");
        return ViewDelegationController.delegateWholePageView(request, PublicDataViewNames.VIEW_ABOUT_ORGANIZATION);
    }

    @RequestMapping(value = "aboutLeadership")
    public ModelAndView aboutLeadership(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ pubic/aboutLeadership.htm");
        return ViewDelegationController.delegateWholePageView(request, PublicDataViewNames.VIEW_ABOUT_LEADERSHIP);
    }

    @RequestMapping(value = "achievements")
    public ModelAndView achievements(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ pubic/achievements.htm");
        return ViewDelegationController.delegateWholePageView(request, PublicDataViewNames.VIEW_ACHIEVEMENTS);
    }

    @RequestMapping(value = "brochures")
    public ModelAndView brochures(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ pubic/brochures.htm");
        return ViewDelegationController.delegateWholePageView(request, PublicDataViewNames.VIEW_BROCHURES);
    }

    @RequestMapping(value = "calendar")
    public ModelAndView calendar(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ pubic/calendar.htm");
        return ViewDelegationController.delegateWholePageView(request, PublicDataViewNames.VIEW_CALENDAR);
    }

    @RequestMapping(value = "contactUs")
    public ModelAndView contactUs(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ pubic/contactUs.htm");
        return ViewDelegationController.delegateWholePageView(request, PublicDataViewNames.CONTACT_US);
    }

    @RequestMapping(value = "gallery")
    public ModelAndView gallery(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ pubic/gallery.htm");
        return ViewDelegationController.delegateWholePageView(request, PublicDataViewNames.VIEW_GALLERY);
    }

    @RequestMapping(value = "holidays")
    public ModelAndView holidays(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ pubic/holidays.htm");
        return ViewDelegationController.delegateWholePageView(request, PublicDataViewNames.VIEW_HOLIDAYS);
    }

    @RequestMapping(value = "locateUs")
    public ModelAndView locateUs(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ pubic/locateUs.htm");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("organization", organizationService.getOrganization());
        map.put("branches", branchService.getAll());
        map.put("schools", schoolService.getAll());
        return ViewDelegationController.delegateWholePageView(request, PublicDataViewNames.VIEW_LOCATE_US, map);
    }

    @RequestMapping(value = "timetable")
    public ModelAndView timetable(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ pubic/timetable.htm");
        return ViewDelegationController.delegateWholePageView(request, PublicDataViewNames.VIEW_TIMETABLE);
    }

    @RequestMapping(value = "upcomingEvents")
    public ModelAndView upcomingEvents(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ pubic/upcomingEvents.htm");
        return ViewDelegationController.delegateWholePageView(request, PublicDataViewNames.VIEW_UPCOMING_EVENTS);
    }

    @RequestMapping(value = "upcomingExams")
    public ModelAndView upcomingExams(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ pubic/upcomingExams.htm");
        return ViewDelegationController.delegateWholePageView(request, PublicDataViewNames.VIEW_UPCOMING_EXAMS);
    }

}
