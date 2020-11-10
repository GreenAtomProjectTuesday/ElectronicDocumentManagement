package electonic.document.management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public ResponseEntity<String> main() {
        return ResponseEntity.ok("This is the main page. if you can see this that means you are logged in");
    }
}
