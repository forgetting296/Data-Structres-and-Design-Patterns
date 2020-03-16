package com.shusaku.study.patterns.iterator;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-03-02 11:14
 */
public class StringArray implements Aggregate {

    private String[] values;

    public StringArray(String[] values) {
        this.values = values;
    }

    @Override
    public Iterator createIterator() {
        return (Iterator) new StringArrayIterator();
    }

    private class StringArrayIterator implements Iterator {

        private int position;

        @Override
        public Object next() {
            if(this.hasNext()){
                return values[position ++];
            } else {
                return null;
            }
        }

        @Override
        public boolean hasNext() {
            return (position < values.length);
        }
    }
}
