package com.ivarrace.gringotts.infrastructure.config.spring;

import com.ivarrace.gringotts.application.service.*;
import com.ivarrace.gringotts.infrastructure.db.springdata.adapter.*;
import com.ivarrace.gringotts.infrastructure.security.spring.adapter.SpringContextAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBootServiceConfig {

    @Bean
    public AccountancyService accountancyService(AccountancyRepositoryAdapter accountancyRepositoryAdapter, SpringContextAdapter springContextAdapter) {
        return new AccountancyService(accountancyRepositoryAdapter, springContextAdapter);
    }

    @Bean
    public GroupService groupService(GroupRepositoryAdapter groupRepositoryAdapter,
                                     AccountancyService accountancyService) {
        return new GroupService(groupRepositoryAdapter, accountancyService);
    }

    @Bean
    public CategoryService categoryService(CategoryRepositoryAdapter categoryRepositoryAdapter, GroupService groupService) {
        return new CategoryService(categoryRepositoryAdapter, groupService);
    }

    @Bean
    public MovementService movementService(MovementRepositoryAdapter movementRepositoryAdapter, CategoryService categoryService, SpringContextAdapter springContextAdapter, AccountancyUserRoleChecker accountancyUserRoleChecker) {
        return new MovementService(movementRepositoryAdapter, categoryService, springContextAdapter, accountancyUserRoleChecker);
    }

    @Bean
    public UserService userService(SpringContextAdapter adapter,
                                   UserRepositoryAdapter userRepository) {
        return new UserService(adapter, userRepository);
    }

    @Bean
    public AccountancyUserRoleChecker accountancyUserRoleChecker(AccountancyService accountancyService, SpringContextAdapter springContextAdapter) {
        return new AccountancyUserRoleChecker(accountancyService, springContextAdapter);
    }

    @Bean
    public ReportService reportService(MovementService movementService) {
        return new ReportService(movementService);
    }
}