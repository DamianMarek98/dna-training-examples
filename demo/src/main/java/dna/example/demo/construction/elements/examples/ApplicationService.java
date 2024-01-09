package dna.example.demo.construction.elements.examples;

import dna.example.demo.common.Result;

public class ApplicationService {
    //injected
    private final DomainService domainService = new DomainService();
    private final CompanySubscriptionRepository companySubscriptionRepository = new CompanySubscriptionRepository();

    //transaction; logging; authorization etc...
    AppResult doSomething(Long companySubscriptionId) { //return application specific result not domain data
        CompanySubscriptionEntity companySubscriptionEntity = companySubscriptionRepository.findById(companySubscriptionId);
        var result = domainService.doSomething(companySubscriptionEntity);

        if (result.isSuccessful()) {
            companySubscriptionRepository.save(companySubscriptionEntity);
        }
        return AppResult.transform(result);
    }

    //mock
    record AppResult() {
        static AppResult transform(Result result) {
            return new AppResult();
        }
    }

    //mock
    static class CompanySubscriptionRepository {
        CompanySubscriptionEntity findById(Long companySubscriptionId) {
            return new CompanySubscriptionEntity();
        }

        public void save(CompanySubscriptionEntity companySubscriptionEntity) {
            //mock
        }
    }
}
