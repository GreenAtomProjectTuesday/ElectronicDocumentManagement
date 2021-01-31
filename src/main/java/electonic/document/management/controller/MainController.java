package electonic.document.management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MainController {

    @GetMapping
    public ResponseEntity<?> main() {
        return ResponseEntity.ok("This is the main page. if you can see this that means you are logged in");
    }

    @GetMapping("search/{search_model_type}")
    public ResponseEntity<?> search() {
        // TODO: 09.01.2021 call to other searches from here?
        return ResponseEntity.ok("Search result");
    }
}
