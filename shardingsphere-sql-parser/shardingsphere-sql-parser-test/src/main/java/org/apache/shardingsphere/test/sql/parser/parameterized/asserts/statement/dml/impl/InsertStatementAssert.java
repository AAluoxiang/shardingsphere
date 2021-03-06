/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.test.sql.parser.parameterized.asserts.statement.dml.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.SQLCaseAssertContext;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.segment.insert.InsertColumnsClauseAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.segment.insert.InsertValuesClauseAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.segment.insert.OnDuplicateKeyColumnsAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.segment.set.SetClauseAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.segment.table.TableAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.segment.with.WithClauseAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.jaxb.cases.domain.statement.dml.InsertStatementTestCase;
import org.apache.shardingsphere.sql.parser.sql.common.statement.dml.InsertStatement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Insert statement assert.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InsertStatementAssert {
    
    /**
     * Assert insert statement is correct with expected parser result.
     *
     * @param assertContext assert context
     * @param actual actual insert statement
     * @param expected expected insert statement test case
     */
    public static void assertIs(final SQLCaseAssertContext assertContext, final InsertStatement actual, final InsertStatementTestCase expected) {
        assertTable(assertContext, actual, expected);
        assertInsertColumnsClause(assertContext, actual, expected);
        assertInsertValuesClause(assertContext, actual, expected);
        assertSetClause(assertContext, actual, expected);
        assertInsertSelectClause(assertContext, actual, expected);
        assertOnDuplicateKeyColumns(assertContext, actual, expected);
        assertWithClause(assertContext, actual, expected);
    }
    
    private static void assertTable(final SQLCaseAssertContext assertContext, final InsertStatement actual, final InsertStatementTestCase expected) {
        TableAssert.assertIs(assertContext, actual.getTable(), expected.getTable());
    }
    
    private static void assertInsertColumnsClause(final SQLCaseAssertContext assertContext, final InsertStatement actual, final InsertStatementTestCase expected) {
        if (null != expected.getInsertColumnsClause()) {
            assertTrue(assertContext.getText("Actual insert columns segment should exist."), actual.getInsertColumns().isPresent());
            InsertColumnsClauseAssert.assertIs(assertContext, actual.getInsertColumns().get(), expected.getInsertColumnsClause());    
        } else {
            assertFalse(assertContext.getText("Actual insert columns segment should not exist."), actual.getInsertColumns().isPresent());
        }
    }
    
    private static void assertInsertValuesClause(final SQLCaseAssertContext assertContext, final InsertStatement actual, final InsertStatementTestCase expected) {
        if (null != expected.getInsertValuesClause()) {
            assertFalse(assertContext.getText("Actual insert values segment should exist."), actual.getValues().isEmpty());
            InsertValuesClauseAssert.assertIs(assertContext, actual.getValues(), expected.getInsertValuesClause());
        } else {
            assertTrue(assertContext.getText("Actual insert values segment should not exist."), actual.getValues().isEmpty());
        }
    }
    
    private static void assertSetClause(final SQLCaseAssertContext assertContext, final InsertStatement actual, final InsertStatementTestCase expected) {
        if (null != expected.getSetClause()) {
            assertTrue(assertContext.getText("Actual set assignment segment should exist."), actual.getSetAssignment().isPresent());
            SetClauseAssert.assertIs(assertContext, actual.getSetAssignment().get(), expected.getSetClause());
        } else {
            assertFalse(assertContext.getText("Actual set assignment segment should not exist."), actual.getSetAssignment().isPresent());
        }
    }
    
    private static void assertInsertSelectClause(final SQLCaseAssertContext assertContext, final InsertStatement actual, final InsertStatementTestCase expected) {
        if (null != expected.getSelectTestCase()) {
            assertTrue(assertContext.getText("Actual insert select segment should exist."), actual.getInsertSelect().isPresent());
            SelectStatementAssert.assertIs(assertContext, actual.getInsertSelect().get().getSelect(), expected.getSelectTestCase());
        } else {
            assertFalse(assertContext.getText("Actual insert select segment should not exist."), actual.getInsertSelect().isPresent());
        }
    }
    
    private static void assertOnDuplicateKeyColumns(final SQLCaseAssertContext assertContext, final InsertStatement actual, final InsertStatementTestCase expected) {
        if (null != expected.getOnDuplicateKeyColumns()) {
            assertTrue(assertContext.getText("Actual on duplicate key columns segment should exist."), actual.getOnDuplicateKeyColumns().isPresent());
            OnDuplicateKeyColumnsAssert.assertIs(assertContext, actual.getOnDuplicateKeyColumns().get(), expected.getOnDuplicateKeyColumns());
        } else {
            assertFalse(assertContext.getText("Actual on duplicate key columns segment should not exist."), actual.getOnDuplicateKeyColumns().isPresent());
        }
    }
    
    private static void assertWithClause(final SQLCaseAssertContext assertContext, final InsertStatement actual, final InsertStatementTestCase expected) {
        if (null != expected.getWithClause()) {
            assertTrue(assertContext.getText("Actual with segment should exist."), actual.getWithSegment().isPresent());
            WithClauseAssert.assertIs(assertContext, actual.getWithSegment().get(), expected.getWithClause()); 
        } else {
            assertFalse(assertContext.getText("Actual with segment should not exist."), actual.getWithSegment().isPresent());
        }
    }
}
