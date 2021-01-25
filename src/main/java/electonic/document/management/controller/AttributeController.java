package electonic.document.management.controller;

import electonic.document.management.model.document.AttributeValue;
import electonic.document.management.model.document.Document;
import electonic.document.management.service.DocumentAttributeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("attributes")
public class AttributeController {
    private final DocumentAttributeService documentAttributeService;

    public AttributeController(DocumentAttributeService documentAttributeService) {
        this.documentAttributeService = documentAttributeService;
    }

    @PostMapping
    public ResponseEntity<?> registerAttribute(@RequestParam("value") String attributeValue,
                                               @RequestParam("document_id") Document document,
                                               @RequestParam("name") String attributeName) {
        if (!documentAttributeService.registerAttribute(attributeName, document, attributeValue)) {
            return ResponseEntity.ok("Attribute with such name already exists");
        }
        return ResponseEntity.ok("Attribute was successfully registered");
    }

    @PatchMapping("{attribute_id}")
    public ResponseEntity<?> editAttribute(@RequestParam("new_value") String newAttributeValue,
                                           @PathVariable("attribute_id") AttributeValue attributeValue) {
        documentAttributeService.editAttributeValue(attributeValue, newAttributeValue);
        return ResponseEntity.ok("Attribute was successfully edited");
    }

    //TODO + delete by attribute name?
    @DeleteMapping("{attribute_id}")
    public ResponseEntity<?> deleteAttribute(@PathVariable("attribute_id") Long attribute_id) {
        documentAttributeService.deleteAttribute(attribute_id);
        return ResponseEntity.ok("Attribute was successfully deleted");
    }
}
