package io.github.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinSection {
    public enum JoinType{
        LEFT,
        RIGHT,
        INNER,
        JOIN
    }

    public static JoinSectionBuilder builder() {
        return new JoinSectionBuilder();
    }

    //插入类型
    private JoinType type;

    //插入表
    private String table;

    //插入表 as name
    private String asName;

    private String selectSQL;
    //连接条件
    private String connectColumn;

    public static class JoinSectionBuilder {
        private JoinType type;
        private String table;
        private String asName;

        private String selectSQL;
        private String connectColumn;

        JoinSectionBuilder() {
        }

        public JoinSectionBuilder type(final JoinType type) {
            this.type = type;
            return this;
        }

        public JoinSectionBuilder table(final String table) {
            this.table = table;
            return this;
        }

        public JoinSectionBuilder asName(final String asName) {
            this.asName = asName;
            return this;
        }

        public JoinSectionBuilder connectColumn(final String source, final String target) {
            this.connectColumn = source + " = " + target;
            return this;
        }

        public JoinSectionBuilder connectColumn(final String connectColumn) {
            this.connectColumn = connectColumn;
            return this;
        }

        public JoinSectionBuilder selectSQL(final String selectSQL) {
            this.selectSQL = selectSQL;
            return this;
        }

        public JoinSection build() {
            return new JoinSection(this.type, this.table, this.asName,selectSQL, this.connectColumn);
        }

    }
}
