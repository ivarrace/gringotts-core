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
    public AccountancyService accountancyService(AccountancyRepositoryAdapter accountancyRepositoryAdapter,
                                                 SpringContextAdapter springContextAdapter,
                                                 SummaryService summaryService) {
        return new AccountancyService(accountancyRepositoryAdapter, springContextAdapter, summaryService);
    }

    @Bean
    public GroupService groupService(GroupRepositoryAdapter groupRepositoryAdapter,
                                     AccountancyService accountancyService) {
        return new GroupService(groupRepositoryAdapter, accountancyService);
    }

    @Bean
    public CategoryService categoryService(CategoryRepositoryAdapter categoryRepositoryAdapter,
                                           GroupService groupService) {
        return new CategoryService(categoryRepositoryAdapter, groupService);
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
        return new AccountancyUserRoleChecker(accountancyService, springContextAdapter);
    }

    @Bean
    public SummaryService summaryService(MovementService movementService) {
        return new SummaryService(movementService);
    }

}