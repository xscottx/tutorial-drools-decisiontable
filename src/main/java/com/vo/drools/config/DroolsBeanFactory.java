package com.vo.drools.config;

import org.drools.decisiontable.DecisionTableProviderImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

public class DroolsBeanFactory {

    private KieServices kieServices = KieServices.Factory.get();


    public KieSession getKieSession(Resource dt) {
        KieFileSystem kieFileSystem = kieServices
                .newKieFileSystem()
                .write(dt);

        kieServices.newKieBuilder(kieFileSystem)
                .buildAll();

        KieRepository kieRepository = kieServices.getRepository();

        ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();

        KieContainer kieContainer = kieServices.newKieContainer(krDefaultReleaseId);

        return kieContainer.newKieSession();
    }

    // Used just for debugging purposes to verify the Excel file has been unmarshalled correctly
    public String getDrlFromExcel(String excelFile) {
        DecisionTableConfiguration configuration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
        configuration.setInputType(DecisionTableInputType.XLS);

        Resource dt = ResourceFactory.newClassPathResource(excelFile, getClass());

        DecisionTableProviderImpl decisionTableProvider = new DecisionTableProviderImpl();

        String drl = decisionTableProvider.loadFromResource(dt, null);

        return drl;
    }

}
