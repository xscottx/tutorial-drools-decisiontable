package com.vo.drools.service;

import static org.junit.Assert.assertEquals;

import com.vo.drools.model.Offer;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import com.vo.drools.config.DroolsBeanFactory;
import com.vo.drools.model.Customer;

public class DiscountExcelIntegrationTest {

    private KieSession kSession;

    @Before
    public void setup() {
        Resource resource = ResourceFactory.newClassPathResource("com/vo/drools/rules/DroolsDiscount.xlsx", getClass());
        kSession = new DroolsBeanFactory().getKieSession(resource);
        System.out.println(new DroolsBeanFactory().getDrlFromExcel("com/vo/drools/rules/DroolsDiscount.xlsx"));
    }

    @Test
    public void givenMidAsset3Needs_whenFireRule_thenCorrectOffer() {
        // Add a Customer with its personal data and needs, used for the LHS Decision
        Customer customer = new Customer();
        customer.setLifeStage(Customer.CustomerLifeStage.CAREERFOCUSED);
        customer.setAssets(Customer.CustomerAssets.FROM150KTO300K);
        customer.addNeed(Customer.CustomerNeed.LIFEINSURANCE);
        customer.addNeed(Customer.CustomerNeed.SAVINGACCOUNT);
        customer.addNeed(Customer.CustomerNeed.MORTAGE);
        kSession.insert(customer);
//         Now we add the global variable which we use to communicate back
        Offer offer = new Offer();
        kSession.setGlobal("offer", offer);
        kSession.fireAllRules();
        assertEquals(offer.getDiscount(), 10);
        assertEquals(offer.getFinancialPackage(), Offer.ProductPackage.CAREERFOCUSED_PACKAGE);
        assertEquals(offer.getProducts().size(), 2);
        assertEquals(offer.getProducts().contains(Offer.Product.INSURANCE), true);
        assertEquals(offer.getProducts().contains(Offer.Product.LOAN), true);
    }

    @Test
    public void givenCareerFocusedLowAsset_whenFireRule_thenCareerFocusedOffer() {
        Customer customer = new Customer();
        customer.setLifeStage(Customer.CustomerLifeStage.CAREERFOCUSED);
        customer.setAssets(Customer.CustomerAssets.TO50K);
        kSession.insert(customer);

        Offer offer = new Offer();
        kSession.setGlobal("offer", offer);
        kSession.fireAllRules();

        assertEquals(offer.getDiscount(), 0);
        assertEquals(offer.getProducts().size(), 0);
        assertEquals(offer.getFinancialPackage(), Offer.ProductPackage.CAREERFOCUSED_PACKAGE);
    }

    @Test
    public void givenPlanoLocation_whenFireRule_thenCorrectOffer() {
        Customer customer = new Customer();
        customer.setLocation(Customer.CustomerLocation.PLANO);
        kSession.insert(customer);

        Offer offer = new Offer();
        kSession.setGlobal("offer", offer);
        kSession.fireAllRules();

        assertEquals(0, offer.getProducts().size());
        assertEquals(null, offer.getFinancialPackage());
        assertEquals(20, offer.getDiscount());
    }

}
