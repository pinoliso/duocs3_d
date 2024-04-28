package duoc.s3_d.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;

import duoc.s3_d.model.Sale;
import duoc.s3_d.repository.SaleRepository;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {

    @InjectMocks
    private SaleService saleService;

    @Mock
    private SaleRepository saleRepositoryMock;

    @Test
    public void createMovieServiceTest() {
        LocalDate fixedDate = LocalDate.of(2022, 1, 1);
        Sale sale = new Sale();
        sale.setDate(fixedDate);

        when(saleRepositoryMock.save(any())).thenReturn(sale);

        Sale savedSale = saleService.createSale(sale);

        assertEquals(fixedDate, savedSale.getDate());
    }

    @Test
    void deleteSaleTest() {
         LocalDate fixedDate = LocalDate.of(2022, 1, 1);
        Sale sale = new Sale();
        sale.setDate(fixedDate);
        when(saleRepositoryMock.save(any())).thenReturn(sale);
        Sale savedSale = saleService.createSale(sale);

        saleService.deleteSale(savedSale.getId());
        
        verify(saleRepositoryMock, times(1)).deleteById(savedSale.getId());
    }
    
}
