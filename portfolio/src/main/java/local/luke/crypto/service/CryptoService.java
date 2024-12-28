package local.luke.crypto.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import local.luke.crypto.exception.CryptoException;
import local.luke.crypto.model.Crypto;
import local.luke.crypto.model.SortType;

@Service
public class CryptoService {

    private List<Crypto> cryptos = new ArrayList<>();

    public Crypto addCrypto(Crypto crypto) throws CryptoException {
        cryptos.add(crypto);
        return crypto;
    }

    public List<Crypto> getCryptos() {
        return cryptos;
    }

    public List<Crypto> getSortCryptos(String sortParam) throws CryptoException {
        try {
            SortType sortType = SortType.valueOf(sortParam.toUpperCase());
            switch (sortType) {
                case NAME:
                    return cryptos.stream().sorted(Comparator.comparing(Crypto::getName)).collect(Collectors.toList());
                case PRICE:
                    return cryptos.stream().sorted(Comparator.comparing(Crypto::getPrice)).collect(Collectors.toList());
                case QUANTITY:
                    return cryptos.stream().sorted(Comparator.comparing(Crypto::getQuantity)).collect(Collectors.toList());
                default:
                    return cryptos.stream().sorted(Comparator.comparing(Crypto::getName)).collect(Collectors.toList());
            }
        } catch (IllegalArgumentException e) {
            throw new CryptoException("Zadal jsi parametr " + sortParam + ". " + e.getLocalizedMessage());
        }
    }

    public Crypto getCryptoById(Integer id) {
        for (Crypto crypto : cryptos) {
            if (crypto.getId() == id) {
                return crypto;
            }
        }
        return null;
    }

    public Crypto updateCrypto(Integer id, Crypto newCrypto) throws CryptoException {
        Crypto updatedCrypto = getCryptoById(id);

        if (newCrypto.getName() != null) {
            updatedCrypto.setName(newCrypto.getName());
        }
        if (newCrypto.getSymbol() != null) {
            updatedCrypto.setSymbol(newCrypto.getSymbol());
        }
        if (newCrypto.getPrice() != null) {
            updatedCrypto.setPrice(newCrypto.getPrice());
        }
        if (newCrypto.getQuantity() != null) {
            updatedCrypto.setQuantity(newCrypto.getQuantity());
        }
        return updatedCrypto;
    }

    public Map<String, Double> getTotalValue() {
        double totalValue = 0;

        for (Crypto crypto : cryptos) {
            totalValue += (crypto.getPrice() * crypto.getQuantity());
        }

        return Map.of("total-value", totalValue);
    }

}