package com.jrm.backitup_brain.CC;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/auth")
public class Auth {

	@GetMapping("/test")
	public String f_test(){
		return "API IS WORKING";
	}
}
