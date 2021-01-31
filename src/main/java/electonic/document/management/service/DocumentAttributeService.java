package electonic.document.management.service;

import electonic.document.management.model.Message;
import electonic.document.management.model.document.AttributeValue;
import electonic.document.management.model.document.Document;
import electonic.document.management.model.document.DocumentAttribute;
import electonic.document.management.repository.AttributeValueRepository;
import electonic.document.management.repository.DocumentAttributeRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentAttributeService {
    private final DocumentAttributeRepository documentAttributeRepository;
    private final AttributeValueRepository attributeValueRepository;

    public DocumentAttributeService(DocumentAttributeRepository documentAttributeRepository, AttributeValueRepository attributeValueRepository) {
        this.documentAttributeRepository = documentAttributeRepository;
        this.attributeValueRepository = attributeValueRepository;
    }

    @Transactional
    public boolean registerAttribute(String attributeName, Document document, String attributeValueString) {
        DocumentAttribute documentAttributeFromDb = documentAttributeRepository
                .getDocumentAttributeByName(attributeName);

        if (documentAttributeFromDb != null) {
            return false;
        }

        AttributeValue attributeValue = new AttributeValue();
        attributeValue.setValue(attributeValueString);

        DocumentAttribute documentAttribute = new DocumentAttribute();
        documentAttribute.setDocument(document);
        documentAttribute.setName(attributeName);
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

    public List<DocumentAttribute> findDocumentAttributesByExample(String subStringInName, Document document, AttributeValue attributeValue) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withMatcher("name", match -> match.contains());

        DocumentAttribute documentAttribute = new DocumentAttribute();
        documentAttribute.setName(subStringInName);
        documentAttribute.setDocument(document);
        documentAttribute.setAttributeValue(attributeValue);

        Example<DocumentAttribute> documentAttributeExample = Example.of(documentAttribute, matcher);
        return documentAttributeRepository.findAll(documentAttributeExample);
    }
}
