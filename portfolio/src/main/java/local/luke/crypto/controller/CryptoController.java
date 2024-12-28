package local.luke.crypto.controller;

import org.springframework.web.bind.annotation.RestController;
import local.luke.crypto.exception.CryptoException;
import local.luke.crypto.model.Crypto;
import local.luke.crypto.service.CryptoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class CryptoController {

    private CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @PostMapping("/cryptos")
    public ResponseEntity<?> addCrypto(@RequestBody Crypto crypto) {
        try {
            Crypto savedCrypto = cryptoService.addCrypto(crypto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCrypto);
        } catch (CryptoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Chyba při přidání kryptoměny! " + e.getLocalizedMessage());
        }
    }

    @GetMapping("/cryptos")
    public ResponseEntity<?> getSortCryptos(@RequestParam(value = "sort", defaultValue = "name") String sortParam) {
        try {
            List<Crypto> cryptos= cryptoService.getSortCryptos(sortParam);
            return ResponseEntity.status(HttpStatus.OK).body(cryptos);
        } catch (CryptoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Chybný parametr pro řazení! " + e.getLocalizedMessage());
        }
    }

    @GetMapping("/cryptos/{id}")
    public ResponseEntity<?> getCryptoById(@PathVariable Integer id) {
        Crypto crypto = cryptoService.getCryptoById(id);
        if (crypto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nelze nalést záznam pod id " + id + ".");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(crypto);
        }
    }

    @PutMapping("/cryptos/{id}")
    public ResponseEntity<?> updateCrypto(@PathVariable Integer id, @RequestBody Crypto newCrypto) {
        try {
            if (cryptoService.getCryptoById(id) == null) {
                throw new CryptoException("ID záznamu nenalezeno. ");
            }
            Crypto updatedCrypto = cryptoService.updateCrypto(id, newCrypto);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCrypto);
        } catch (CryptoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nelze nalést záznam pod id " + id + ". " + e.getLocalizedMessage());
        }
    }

    @GetMapping("/portfolio-value")
    public ResponseEntity<String> getTotalValue() {
        String totalValue = cryptoService.getTotalValue();
        return ResponseEntity.status(HttpStatus.OK).body("Aktuální hodnota portfolia je " + totalValue + " USD.");
    }

}
