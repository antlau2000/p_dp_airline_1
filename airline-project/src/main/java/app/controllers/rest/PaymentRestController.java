package app.controllers.rest;

import app.controllers.api.rest.PaymentRestApi;
import app.entities.Payment;
import app.services.interfaces.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentRestController implements PaymentRestApi {

    private final PaymentService paymentService;

    @Override
    public ResponseEntity<Page<Payment>> getAll(Integer page, Integer count) {
        List<Payment> payments = paymentService.findAllPayments();
        if (payments == null) {
            log.info("getListOfAllPayments: not found any payments");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("getListOfAllPayments: found {} payments", payments.size());
        return new ResponseEntity<>(paymentService.pagePagination(page, count), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Payment> get(Long id) {
        Payment payment = paymentService.findPaymentById(id);
        if (payment != null) {
            log.info("get: find payment with id = {}", id);
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } else {
            log.info("get: not find payment with id = {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> create(Payment payment) {
        try {
            Payment savedPayment = paymentService.savePayment(payment);
            log.info("create: new payment saved with id= {}", savedPayment.getId());
            return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            log.error("create: some bookings not exist");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}