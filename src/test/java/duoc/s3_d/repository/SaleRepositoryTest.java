package duoc.s3_d.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import duoc.s3_d.model.Sale;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SaleRepositoryTest {
    
    @Mock
    private SaleRepository SaleRepositoryMock;

    @Test
    public void saveSaleRepositoryTest() {
        LocalDate fixedDate = LocalDate.of(2022, 1, 1);
        Sale sale = new Sale();
        sale.setDate(fixedDate);

        when(SaleRepositoryMock.save(any())).thenReturn(sale);

        Sale savedSale = SaleRepositoryMock.save(sale);

        assertEquals(fixedDate, savedSale.getDate());
    }

    @Test
    public void deleteSaleRespositoryTest() {
        LocalDate fixedDate = LocalDate.of(2022, 1, 1);
        Sale sale = new Sale();
        sale.setDate(fixedDate);
        when(SaleRepositoryMock.save(any())).thenReturn(sale);
        Sale savedSale = SaleRepositoryMock.save(sale);

        SaleRepositoryMock.deleteById(savedSale.getId());
        
        verify(SaleRepositoryMock, times(1)).deleteById(savedSale.getId());
    }

}
