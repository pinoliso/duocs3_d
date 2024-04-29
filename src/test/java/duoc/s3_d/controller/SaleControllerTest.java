package duoc.s3_d.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import duoc.s3_d.model.Sale;
import duoc.s3_d.service.SaleService;
import duoc.s3_d.service.ProductService;

import java.util.List;

@WebMvcTest(SaleController.class)
public class SaleControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaleService saleServiceMock;

    @MockBean
    private ProductService productServiceMock;

    @Test
    public void testGetAllSales() throws Exception {
        Sale sale1 = new  Sale();
        sale1.setId(22L);
        Sale sale2 = new  Sale();
        sale2.setId(44L);
        List< Sale> sales = List.of(sale1, sale2);
        when(saleServiceMock.getAllSales()).thenReturn(sales);

        mockMvc.perform(MockMvcRequestBuilders.get("/sales"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.saleList.[0].id").value(22))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.saleList.[1].id").value(44));
    }

    @Test
    void deleteSaleTest() throws Exception {
        Long saleIdToDelete = 1123L;

        mockMvc.perform(delete("/sales/{id}", saleIdToDelete))
                .andExpect(status().isNotFound());
    }

}

