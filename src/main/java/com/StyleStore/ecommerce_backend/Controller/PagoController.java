package com.StyleStore.ecommerce_backend.Controller;

import com.StyleStore.ecommerce_backend.Dto.PaymentRequestDto;
import com.StyleStore.ecommerce_backend.Service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/create-payment")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        try {
            String url = stripeService.crearPagoConStripe(paymentRequestDto.getMonto(), paymentRequestDto.getCurrency(), paymentRequestDto.getPedidoId());
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el pago: " + e.getMessage());
        }
    }
}

