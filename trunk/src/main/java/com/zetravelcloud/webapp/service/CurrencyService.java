package com.zetravelcloud.webapp.service;

import com.zetravelcloud.webapp.domain.Currency;
import com.zetravelcloud.webapp.repository.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Currency.
 */
@Service
@Transactional
public class CurrencyService {

    private final Logger log = LoggerFactory.getLogger(CurrencyService.class);
    
    @Inject
    private CurrencyRepository currencyRepository;
    
    /**
     * Save a currency.
     * @return the persisted entity
     */
    public Currency save(Currency currency) {
        log.debug("Request to save Currency : {}", currency);
        Currency result = currencyRepository.save(currency);
        return result;
    }

    /**
     *  get all the currencys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Currency> findAll() {
        log.debug("Request to get all Currencys");
        List<Currency> result = currencyRepository.findAll();
        return result;
    }

    /**
     *  get one currency by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Currency findOne(Long id) {
        log.debug("Request to get Currency : {}", id);
        Currency currency = currencyRepository.findOne(id);
        return currency;
    }

    /**
     *  delete the  currency by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Currency : {}", id);
        currencyRepository.delete(id);
    }
}
