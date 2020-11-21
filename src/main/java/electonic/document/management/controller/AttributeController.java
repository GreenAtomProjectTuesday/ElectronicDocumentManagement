package electonic.document.management.controller;

import electonic.document.management.model.AttributeValue;
import electonic.document.management.model.Document;
import electonic.document.management.service.DocumentAttributeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("attribute")
public class AttributeController {
    private final DocumentAttributeService documentAttributeService;

    public AttributeController(DocumentAttributeService documentAttributeService) {
        this.documentAttributeService = documentAttributeService;
    }

    @PostMapping("register")
    public ResponseEntity<String> registerAttribute(@RequestParam("value") String attributeValue,
                                                    @RequestParam("document_id") Document document,
                                                    @RequestParam("attribute_name") String attributeName) {
        if (!documentAttributeService.registerAttribute(attributeName, document, attributeValue)) {
            return ResponseEntity.ok("Attribute with such name already exists");
        }
        return ResponseEntity.ok("Attribute was successfully registered");
    }

    @PostMapping("edit")
    public ResponseEntity<String> editAttribute(@RequestParam("new_value") String newAttributeValue,
                                                @RequestParam("attribute_id") AttributeValue attributeValue) {
        documentAttributeService.editAttributeValue(attributeValue, newAttributeValue);
        return ResponseEntity.ok("Attribute was successfully edited");
    }

    //TODO + delete by attribute name?
    @PostMapping("delete")
    public ResponseEntity<String> deleteAttribute(@RequestParam("attribute_id") Long attribute_id) {
        documentAttributeService.deleteAttribute(attribute_id);
        return ResponseEntity.ok("Attribute was successfully edited");
    }
}