package ouiplusplus.controller;

import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ouiplusplus.start.ServerStart;

@CrossOrigin(origins = "*")
@RestController
public class OuiPlusPlusServer {
	
	@GetMapping("hello")
    public String helloWorld(@RequestParam(required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "Hello, World!";
    }
	
	
	@PostMapping("/process")
	@ResponseBody
	public ResponseEntity<String> postResponseJsonContent(
	  @RequestBody RequestBodyCode input) {
		String rtn = ServerStart.process(input.getInput());
		
		return ResponseEntity.ok().body(rtn);
	}
}