package electonic.document.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Message;
import electonic.document.management.model.Views;
import electonic.document.management.model.document.AttributeValue;
import electonic.document.management.model.document.Document;
import electonic.document.management.model.document.DocumentAttribute;
import electonic.document.management.service.DocumentAttributeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("attributes")
public class AttributeController {
    private final DocumentAttributeService documentAttributeService;
    private final ObjectMapper objectMapper;

    public AttributeController(DocumentAttributeService documentAttributeService, ObjectMapper objectMapper) {
        this.documentAttributeService = documentAttributeService;
        this.objectMapper = objectMapper;
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

    @GetMapping("with_params")
    public ResponseEntity<?> findDocumentAttributes(
            @RequestParam(value = "name_contains", required = false) String subStringInName,
            @RequestParam(value = "document_id", required = false) Document document,
            @RequestParam(value = "attribute_value_id", required = false) AttributeValue attributeValue
    ) throws JsonProcessingException {
        if (subStringInName == null && document == null)
            return ResponseEntity.ok("No parameters were specified");
        List<DocumentAttribute> departmentsWithParams = documentAttributeService
                .findDocumentAttributesByExample(subStringInName, document, attributeValue);
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.FullClass.class)
                .writeValueAsString(departmentsWithParams));
    }
}
