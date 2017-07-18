package com.redhat.crowdfunding.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.crypto.CipherException;

import com.alibaba.fastjson.JSON;
import com.redhat.crowdfunding.model.Fund;
import com.redhat.crowdfunding.service.CrowdFundingService;
import com.redhat.crowdfunding.service.CrowdFundingServiceImpl;

/**
 * @author littleredhat
 */
@Controller
public class CrowdFundingController {

	@RequestMapping("list")
	public String List(HttpServletRequest request, HttpServletResponse response) {
		try {
			CrowdFundingService service = new CrowdFundingServiceImpl();
			int totalNum = service.getFundCount();
			request.setAttribute("totalNum", totalNum);
			return "list";
		} catch (IOException | CipherException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return "error";
	}

	@RequestMapping("getFunds")
	@ResponseBody
	public String getFunds(HttpServletRequest request, HttpServletResponse response) {
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		try {
			CrowdFundingService service = new CrowdFundingServiceImpl();
			List<Fund> data = service.getFunds(pageIndex);
			System.out.println("[getFunds] " + JSON.toJSONString(data)); // test
			return JSON.toJSONString(data);
		} catch (IOException | CipherException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return "";
	}

	@RequestMapping("raiseFund")
	@ResponseBody
	public boolean raiseFund(HttpServletRequest request, HttpServletResponse response) {
		String owner = request.getParameter("owner");
		System.out.println("[raiseFund] " + owner); // test
		try {
			CrowdFundingService service = new CrowdFundingServiceImpl();
			return service.raiseFund(owner);
		} catch (IOException | CipherException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}

	@RequestMapping("sendCoin")
	@ResponseBody
	public boolean sendCoin(HttpServletRequest request, HttpServletResponse response) {
		String owner = request.getParameter("owner");
		int coin = Integer.parseInt(request.getParameter("coin"));
		String password = request.getParameter("password");
		String content = request.getParameter("content");
		System.out.println("[sendCoin] " + owner + " " + coin + " " + password + " " + content); // test
		try {
			CrowdFundingService service = new CrowdFundingServiceImpl(password, content);
			return service.sendCoin(owner, coin);
		} catch (IOException | CipherException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}
}
