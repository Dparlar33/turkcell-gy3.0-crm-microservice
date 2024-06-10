package com.turkcell.crm.catalogService.business.rules;

import com.turkcell.crm.catalogService.business.messages.ProductPropertyMessages;
import com.turkcell.crm.catalogService.core.business.abstracts.MessageService;
import com.turkcell.crm.catalogService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.crm.catalogService.dataAccess.ProductPropertyRepository;
import com.turkcell.crm.catalogService.entity.ProductProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProductPropertyBusinessRulesTest {

    @Mock
    private ProductPropertyRepository productPropertyRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private ProductPropertyBusinessRules businessRules;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsProductPropertyExistById_WhenProductPropertyExists() {
        // Arrange

        ProductProperty existingProductProperty = new ProductProperty();
        existingProductProperty.setId(1);
        when(productPropertyRepository.findById(existingProductProperty.getId())).thenReturn(Optional.of(existingProductProperty));

        // Act
        ProductProperty result = businessRules.isProductPropertyExistById(existingProductProperty.getId());

        // Assert
        assertNotNull(result);
        assertEquals(existingProductProperty, result);
    }

    @Test
    public void testIsProductPropertyExistById_WhenProductPropertyDoesNotExist() {
        // Arrange
        ProductProperty existingProductProperty = new ProductProperty();
        existingProductProperty.setId(1);
        when(productPropertyRepository.findById(existingProductProperty.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BusinessException.class, () -> businessRules.isProductPropertyExistById(existingProductProperty.getId()));
    }

    @Test
    public void testIsProductPropertyAlreadyDeleted_WhenProductPropertyNotDeleted() {
        // Arrange

        ProductProperty existingProductProperty = new ProductProperty();
        existingProductProperty.setId(1);
        when(productPropertyRepository.findById(existingProductProperty.getId())).thenReturn(Optional.of(existingProductProperty));

        // Act
        ProductProperty result = businessRules.isProductPropertyAlreadyDeleted(existingProductProperty.getId());

        // Assert
        assertNotNull(result);
        assertEquals(existingProductProperty, result);
    }

    @Test
    public void testIsProductPropertyAlreadyDeleted_WhenProductPropertyAlreadyDeleted() {
        // Arrange

        ProductProperty deletedProductProperty = new ProductProperty();
        deletedProductProperty.setId(1);
        deletedProductProperty.setDeletedDate(LocalDateTime.now());
        when(productPropertyRepository.findById(deletedProductProperty.getId())).thenReturn(Optional.of(deletedProductProperty));
        when(messageService.getMessage(ProductPropertyMessages.PRODUCT_PROPERTY_ALREADY_DELETED))
                .thenReturn("Product property already deleted");

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> businessRules.isProductPropertyAlreadyDeleted(deletedProductProperty.getId()));
        assertEquals("Product property already deleted", exception.getMessage());
    }
}