package com.ivarrace.gringotts.domain.accountancy;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Report {

    private LocalDateTime createdDate;
    private LocalDateTime lastModified;
    private int movementsNumber;
}
/**
 * POR CUENTA (ej Personal o Compartida)
 * ----------------------------
 * año 2022
 * mensual:
 *    Enero:
 *      ingresos: 3
 *      gastos: 2
 *      ahorro neto: 1
 *    Febrero:
 *      ingresos: 3
 *      gastos: 2
 *      ahorro neto: 1
 *    .
 *    .
 *    .
 *    Diciembre:
 *      ingresos: 3
 *      gastos: 2
 *      ahorro neto: 1
 * Total:
 *   ingresos: 300
 *   gastos: 200
 *   ahorro neto: 100
 * Promedio:
 *   ingresos: 300
 *   gastos: 200
 *   ahorro neto: 100
 */

/**
 * POR TIPO (ej Personal/incomes o Personal/expenses)
 * ----------------------------
 * año 2022
 * reporte mensual
 * mensual:
 *    Enero:
 *      grupo1: 3
 *      grupo2: 2
 *      total: 5
 *    Febrero:
 *      grupo1: 3
 *      grupo2: 2
 *      total: 5
 *    .
 *    .
 *    .
 *    Diciembre:
 *      grupo1: 3
 *      grupo2: 2
 *      total: 5
 * Total:
 *   cantidad: 300
 * Promedio:
 *   cantidad: 300
 */

/**
 * EN UN FUTURO
 */

/**
 * POR GRUPO (ej Personal/incomes/trabajo o Personal/expenses/coche)
 * ----------------------------
 * año 2022
 * reporte mensual
 * mensual:
 *    Enero:
 *      categoria1: 3
 *      categoria2: 2
 *      total: 5
 *    Febrero:
 *      categoria1: 3
 *      categoria2: 2
 *      total: 5
 *    .
 *    .
 *    .
 *    Diciembre:
 *      categoria1: 3
 *      categoria2: 2
 *      total: 5
 * Total:
 *   cantidad: 300
 * Promedio:
 *   cantidad: 300
 */

/**
 * POR CATEGORIA (ej Personal/incomes/trabajo/salario o Personal/expenses/coche/seguro)
 * ----------------------------
 * año 2022
 * reporte mensual
 * mensual:
 *    Enero:
 *      total: 5
 *    Febrero:
 *      total: 5
 *    .
 *    .
 *    .
 *    Diciembre:
 *      total: 5
 * Total:
 *   cantidad: 300
 * Promedio:
 *   cantidad: 300
 */