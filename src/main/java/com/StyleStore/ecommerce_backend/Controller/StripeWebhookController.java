package com.StyleStore.ecommerce_backend.Controller;

import com.StyleStore.ecommerce_backend.Model.Pedido;
import com.StyleStore.ecommerce_backend.Repository.PedidoRepository;
import com.StyleStore.ecommerce_backend.exception.ResourceNotFoundException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


@RestController
@RequestMapping("/api/stripe")
public class StripeWebhookController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeEvent(HttpServletRequest request) {
        String payload;
        String sigHeader = request.getHeader("Stripe-Signature");
        try {
            payload = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al procesar la solicitud");
        }

        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Firma inválida");
        }

        // Si el pago fue completado exitosamente
        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().orElseThrow();

            // Aquí obtienes el ID del pedido que habías pasado en la sesión de pago
            String pedidoId = session.getClientReferenceId();  // Este ID debe ser el del pedido en tu sistema

            // Busca el pedido en la base de datos
            Pedido pedido = pedidoRepository.findById(UUID.fromString(pedidoId))
                    .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));

            // Actualiza el estado del pedido a "Confirmado"
            pedido.setEstado("Confirmado");
            pedidoRepository.save(pedido);  // Guardar los cambios en la base de datos

            return ResponseEntity.ok("Pedido confirmado exitosamente");
        }

        return ResponseEntity.ok("Webhook procesado correctamente");
    }
}

