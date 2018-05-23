package com.drools;

import org.drools.template.DataProviderCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

	@Bean
	public KieContainer kieContainer() {

		return this.getKieContainerDefault();
		//return this.getKieContainerByBuilding();
	}

	public KieContainer getKieContainerDefault() {
		KieServices kieServices = KieServices.Factory.get();
		return  kieServices.getKieClasspathContainer();
	}
	public KieContainer getKieContainerByBuilding(){
		KieServices kieServices = KieServices.Factory.get();

		String drlHello1File = "org.rules/helloworld1/HelloWorld1.drl";
		String drlHello2File = "org.rules/helloworld2/HelloWorld2.drl";
		String kmoduleFile = "META-INF/kmodule.xml";


		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

		KieRepository kieRepo = kieServices.getRepository();

		kieFileSystem.write(ResourceFactory.newClassPathResource(drlHello1File)); // kSession = HelloWorld1KS
		kieFileSystem.write(ResourceFactory.newClassPathResource(drlHello2File)); // kSession = HelloWorld2KS

		//kieRepo.addKieModule(ResourceFactory.newClassPathResource(kmoduleFile)); // Doesn't seem to work

		//TODO: build rules from database and add them to an existing kbase or ksession.

		KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);

		kieBuilder.buildAll();

		if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {

			throw new RuntimeException("Build Errors:\n" + kieBuilder.getResults().toString());

		}

		KieModule kieModule = kieBuilder.getKieModule();

		return kieServices.newKieContainer(kieModule.getReleaseId());
	}
}
