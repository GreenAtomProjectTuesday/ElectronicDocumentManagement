package electonic.document.management.service;

import electonic.document.management.model.AttributeValue;
import electonic.document.management.model.Document;
import electonic.document.management.model.DocumentAttribute;
import electonic.document.management.repository.AttributeValueRepository;
import electonic.document.management.repository.DocumentAttributeRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentAttributeService {
    private final DocumentAttributeRepository documentAttributeRepository;
    private final AttributeValueRepository attributeValueRepository;

    public DocumentAttributeService(DocumentAttributeRepository documentAttributeRepository, AttributeValueRepository attributeValueRepository) {
        this.documentAttributeRepository = documentAttributeRepository;
        this.attributeValueRepository = attributeValueRepository;
    }

    //TODO add transaction?
    public boolean registerAttribute(String attributeName, Document document, String attributeValueString) {
        DocumentAttribute documentAttributeFromDb = documentAttributeRepository
                .getDocumentAttributeByAttributeName(attributeName);

        if (documentAttributeFromDb != null) {
            return false;
        }

        AttributeValue attributeValue = new AttributeValue();
        attributeValue.setValue(attributeValueString);

        DocumentAttribute documentAttribute = new DocumentAttribute();
        documentAttribute.setDocument(document);
        documentAttribute.setAttributeName(attributeName);
        documentAttribute.setAttributeValue(attributeValue);

        attributeValueRepository.save(attributeValue);
        documentAttributeRepository.save(documentAttribute);
        return true;
    }

    public void editAttributeValue(AttributeValue attributeValue, String newAttributeValue) {
        attributeValue.setValue(newAttributeValue);
        attributeValueRepository.save(attributeValue);
    }

    public void deleteAttribute(Long attributeValue) {
        documentAttributeRepository.deleteById(attributeValue);
    }
}