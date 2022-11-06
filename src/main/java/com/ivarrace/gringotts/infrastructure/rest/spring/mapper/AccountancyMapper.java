package com.ivarrace.gringotts.infrastructure.rest.spring.mapper;

import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.Utils;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.AccountancyResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.NewAccountancyCommand;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AccountancyMapper {

    private AccountancyMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static AccountancyResponse toResponse(Accountancy accountancy) {
        if (accountancy == null) {
            return null;
        }
        AccountancyResponse accountancyResponse = new AccountancyResponse();
        accountancyResponse.setId(accountancy.getId());
        accountancyResponse.setName(accountancy.getName());
        accountancyResponse.setCreatedDate(accountancy.getCreatedDate());
        accountancyResponse.setLastModified(accountancy.getLastModified());
        accountancyResponse.setIncomes(GroupMapper.toResponse(accountancy.getIncomes()));
        accountancyResponse.setExpenses(GroupMapper.toResponse(accountancy.getExpenses()));
        accountancyResponse.setKey(accountancy.getKey());
        return accountancyResponse;
    }

    public static List<AccountancyResponse> toResponse(List<Accountancy> accountancyList) {
        if (accountancyList == null || accountancyList.isEmpty()) {
            return Collections.emptyList();
        }
        return accountancyList.stream().map(AccountancyMapper::toResponse).collect(Collectors.toList());
    }

    public static Accountancy toDomain(NewAccountancyCommand command) {
        if (command == null) {
            return null;
        }
        Accountancy accountancy = new Accountancy();
        accountancy.setName(command.getName());
        accountancy.setKey(Utils.nameToKey(command.getName()));
        return accountancy;
    }

}