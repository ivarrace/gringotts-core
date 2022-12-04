package com.ivarrace.gringotts.infrastructure.config.spring;

import com.ivarrace.gringotts.application.service.*;
import com.ivarrace.gringotts.infrastructure.db.springdata.adapter.*;
import com.ivarrace.gringotts.infrastructure.security.spring.adapter.SpringContextAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class SpringBootServiceConfig {

    @Bean
    public AccountancyService accountancyService(SpringContextAdapter springContextAdapter,
                                                 AccountancyRepositoryAdapter accountancyRepositoryAdapter,
                                                 SummaryService summaryService) {
        return new AccountancyService(springContextAdapter, accountancyRepositoryAdapter, summaryService);
    }

    @Bean
    public GroupService groupService(SpringContextAdapter springContextAdapter,
                                     AccountancyService accountancyService,
                                     GroupRepositoryAdapter groupRepositoryAdapter) {
        return new GroupService(springContextAdapter, accountancyService, groupRepositoryAdapter);
    }

    @Bean
    public CategoryService categoryService(SpringContextAdapter springContextAdapter,
                                           GroupService groupService,
                                           CategoryRepositoryAdapter categoryRepositoryAdapter) {
        return new CategoryService(springContextAdapter, groupService, categoryRepositoryAdapter);
    }

    @Bean //TODO fix Lazy
    public MovementService movementService(MovementRepositoryAdapter movementRepositoryAdapter,
                                           @Lazy CategoryService categoryService,
                                           SpringContextAdapter springContextAdapter,
                                           @Lazy AccountancyUserRoleChecker accountancyUserRoleChecker) {
        return new MovementService(movementRepositoryAdapter, categoryService, springContextAdapter,
                accountancyUserRoleChecker);
    }

    @Bean
    public UserService userService(UserRepositoryAdapter userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    public AccountancyUserRoleChecker accountancyUserRoleChecker(AccountancyService accountancyService,
                                                                 SpringContextAdapter springContextAdapter) {
        return new AccountancyUserRoleChecker(springContextAdapter, accountancyService);
    }

    @Bean
    public SummaryService summaryService(MovementService movementService) {
        return new SummaryService(movementService);
    }

}