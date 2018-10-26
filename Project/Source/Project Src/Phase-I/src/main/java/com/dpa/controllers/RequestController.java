package com.dpa.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dpa.model.Request;
import com.dpa.service.RequestService;

@Controller
public class RequestController {
	@Autowired
	RequestService requestService;
	
	@RequestMapping(value = "/sendrequest", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			String userName = (String) session.getAttribute("userName");
		} 
		return "advisorrequest";
	}
	@RequestMapping(value = "/sendrequest", method = RequestMethod.POST)
	public String sendRequest(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Request sendRequest, ModelMap model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			String userName = (String) session.getAttribute("userName");
		} 
		int result = requestService.sendRequest(sendRequest);
		return "studenthome";
	}
	@RequestMapping(value = "/sentrequests", method = RequestMethod.GET)
	public String getSentRequests(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		HttpSession session = request.getSession(false);
		List<Request> sentRequests = new ArrayList<Request>();
		if (session != null) {
			String userName = (String) session.getAttribute("userName");
			model.addAttribute("sentRequests", requestService.getSentRequests(userName));
			return "sentrequests";
		}else {
		return "login";
		}
	}
	
	@RequestMapping(value = "/receivedrequests", method = RequestMethod.GET)
	public String getReceivedRequests(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		HttpSession session = request.getSession(false);
		List<Request> receivedRequests = new ArrayList<Request>();
		if (session != null) {
			String userName = (String) session.getAttribute("userName");
			model.addAttribute("receivedRequests", requestService.getReceivedRequests(userName));
			return "receivedrequests";
		}else {
		return "login";
		}
	}
	@RequestMapping(value = "/acceptrequest", method = RequestMethod.POST, consumes="application/json")
	public String acceptRequest(@RequestBody String details, HttpServletRequest request, HttpServletResponse response, ModelMap model){
		String[] majorProfessor = details.split("\\[");
		String[] studentName = majorProfessor[1].split("\\]");
		String[] studentMajor = majorProfessor[2].split("\\]");
		String[] professorName = majorProfessor[3].split("\\]");
		String[] professorEmail = majorProfessor[4].split("\\]");
		String sName = studentName[0].replaceAll("^\"|\"$", "");
		String pName = studentMajor[0].replaceAll("^\"|\"$", "");
		String pEmail = professorName[0].replaceAll("^\"|\"$", "");
		String sMajor = professorEmail[0].replaceAll("^\"|\"$", "");
		HttpSession session = request.getSession(false);
		if (session != null) {
			String userName = (String) session.getAttribute("userName");
			int Result = requestService.acceptRequest(sName, sMajor, pName, pEmail);
			return "receivedrequests";
		}else {
		return "login";
		}
	}
	@RequestMapping(value = "/deleterequest", method = RequestMethod.POST, consumes="application/json")
	public String deleteRequest(@RequestBody String details, HttpServletRequest request, HttpServletResponse response, ModelMap model){
		String[] toDelete = details.split("\\[");
		String[] professorEmail = toDelete[1].split("\\]");
		String[] studentName = toDelete[2].split("\\]");
		String pEmail = professorEmail[0].replaceAll("^\"|\"$", "");
		String sName = studentName[0].replaceAll("^\"|\"$", "");
		HttpSession session = request.getSession(false);
		if (session != null) {
			String userName = (String) session.getAttribute("userName");
			int Result = requestService.deleteRequest(pEmail, sName);
			return "studenthome";
		}else {
		return "login";
		}
	}
	
}