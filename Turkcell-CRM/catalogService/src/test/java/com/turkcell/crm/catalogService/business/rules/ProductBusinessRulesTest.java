package com.turkcell.crm.catalogService.business.rules;

import com.turkcell.crm.catalogService.business.messages.ProductMessages;
import com.turkcell.crm.catalogService.core.business.abstracts.MessageService;
import com.turkcell.crm.catalogService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.crm.catalogService.dataAccess.ProductRepository;
import com.turkcell.crm.catalogService.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductBusinessRulesTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private ProductBusinessRules productBusinessRules;

    @Test
    public void testIsProductExistById_ProductExists() {
        // Arrange
        Product product = new Product();
        product.setId(1);
        product.setName("Test Product");
        product.setCatalog(null);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // Act
        Product result = productBusinessRules.isProductExistById(product.getId());

        // Assert
        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
    }

    @Test
    public void testIsProductExistById_ProductDoesNotExist() {
        // Arrange
        Product product = new Product();
        product.setId(1);
        product.setName("Test Product");
        product.setCatalog(null);

        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
        when(messageService.getMessage(ProductMessages.PRODUCT_NOT_FOUND))
                .thenReturn("Product not found message");

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> productBusinessRules.isProductExistById(product.getId()));
        assertEquals("Product not found message", exception.getMessage());
    }

    @Test
    public void testIsProductAlreadyDeleted_ProductNotDeleted() {
        // Arrange

        Product product = new Product();
        product.setId(2);
        product.setName("Test Product");
        product.setCatalog(null);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // Act
        Product result = productBusinessRules.isProductAlreadyDeleted(product.getId());

        // Assert
        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
    }

    @Test
    public void testIsProductAlreadyDeleted_ProductAlreadyDeleted() {
        // Arrange
        Product product = new Product();
        product.setId(2);
        product.setName("Test Product");
        product.setCatalog(null);
        product.setDeletedDate(LocalDateTime.now()); // Set deleted date to simulate deleted product

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(messageService.getMessage(ProductMessages.PRODUCT_ALREADY_DELETED))
                .thenReturn("Product already deleted message");

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> productBusinessRules.isProductAlreadyDeleted(product.getId()));
        assertEquals("Product already deleted message", exception.getMessage());
    }

}