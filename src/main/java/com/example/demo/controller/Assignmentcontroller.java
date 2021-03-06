package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Assignment;
import com.example.demo.model.Employee;
import com.example.demo.model.Job;
import com.example.demo.model.Position;
import com.example.demo.model.Registration;
import com.example.demo.service.AssignmentSv;
import com.example.demo.service.EmployeeSv;
import com.example.demo.service.JobSv;
import com.example.demo.service.RegistrationSv;

@Controller
public class Assignmentcontroller {
	@Autowired
	private AssignmentSv assignmentSv;
	@Autowired
	private JobSv servicecv;
	@Autowired
	private RegistrationSv service1;
	@Autowired
	private EmployeeSv service;
	@GetMapping("/viewAssignment")
	public String viewindex(Model model) {
		model.addAttribute("listAssignment",assignmentSv.listAll());
		return "viewAssignment";
	}

	@GetMapping("/filterallsalary")
	public String view() {
		return "filterallsalary";
	}
	
	@GetMapping("/filtersalaryuser")
	public String viewsalaryuser() {
		return "filtersalaryuser";
	}
	
	@PostMapping("/salaryall")
	public String viewtest(@RequestParam(value="start") String start ,@RequestParam(value="end") String end, Model model) {
		model.addAttribute("listchitiet",assignmentSv.getAllSalary(start,end));
		return "salaryall";
	}
	
//	@PostMapping("/salaryuser")
//	public String viewtest(@RequestParam(value="start") String start ,@RequestParam(value="end") String end,@RequestParam(value="idemp") Integer idemp, Model model) {
//		model.addAttribute("listchitiet",assignmentSv.getSalary(start,end,idemp));
//		return "salaryuser";
//	}
	
	@RequestMapping(value = "/saveAssignment", method = RequestMethod.POST)
	public String saveAssignment(@ModelAttribute("Assignment") Assignment Assignment) {
		assignmentSv.save(Assignment);
	    return "redirect:/viewAssignment";
	}
	
	@RequestMapping("/createAssignment")
	public String showNewChamCongPage(Model model) {
	    Assignment Assignment = new Assignment();
	    model.addAttribute("Assignment", Assignment);	

	    List<Job> listJob= servicecv.listAll();
	    model.addAttribute("listJob", listJob);
	    
		List<Registration> listRegistration = service1.listAll();
		model.addAttribute("listregistrationAll", listRegistration);
	    List<Registration> listJobSang= service1.Jobsang();
	    model.addAttribute("listregistrationM", listJobSang);
	    List<Registration> listJobChieu= service1.Jobchieu();
	    model.addAttribute("listregistrationA", listJobChieu);
	    List<Registration> listJobToi= service1.Jobtoi();
	    model.addAttribute("listregistrationE", listJobToi);
	    return "createAssignment";
	}
	
	@RequestMapping("/editAssignment/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id, Model model) {
		ModelAndView mav = new ModelAndView("editAssignment");
		Assignment Assignment = assignmentSv.get(id);
		
		mav.addObject("assignment", Assignment);
		
	    List<Job> listJob= servicecv.listAll();
	    model.addAttribute("listJob", listJob);
	    
		List<Registration> listRegistration = service1.listAll();
		model.addAttribute("listregistrationAll", listRegistration);
	    List<Registration> listJobSang= service1.Jobsang();
	    model.addAttribute("listregistrationM", listJobSang);
	    List<Registration> listJobChieu= service1.Jobchieu();
	    model.addAttribute("listregistrationA", listJobChieu);
	    List<Registration> listJobToi= service1.Jobtoi();
	    model.addAttribute("listregistrationE", listJobToi);

		return mav;
	}

	@RequestMapping("/deleteAssignment/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		assignmentSv.delete(id);
		return "redirect:/viewAssignment";
	}
	//USER
//	@GetMapping("/viewuserAssignment")
//	public String viewuserassignment(Model model) {
//		model.addAttribute("listAssignment",assignmentSv.listAll());
//		return "viewuserAssignment";
//	}
	
	@GetMapping("/viewuserAssignment")
	public String viewuserassignment(Model model, Authentication auth) {
		Employee e = service.findByUsername(auth.getName());
		model.addAttribute("listAssignment",assignmentSv.listUserAssign(e.getIdemp()));
		return "viewuserAssignment";
	}
	
	@PostMapping("/salaryuser")
	public String viewtest(@RequestParam(value="start") String start ,@RequestParam(value="end") String end, Authentication auth, Model model) {
		Employee e = service.findByUsername(auth.getName());
		model.addAttribute("listchitiet",assignmentSv.getSalary(start,end,e.getIdemp()));
		return "salaryuser";
	}
}
















