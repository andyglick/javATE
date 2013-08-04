package it.amattioli.dominate.specifications;

public enum TotalOrderComparisonType {
	EQUAL {
		@Override
		public <T extends Comparable<T>> boolean isSatisfiedBy(T arg1, T arg2) {
			return arg1.compareTo(arg2) == 0;
		}
	},
	NOT_EQUAL {
		@Override
		public <T extends Comparable<T>> boolean isSatisfiedBy(T arg1, T arg2) {
			return arg1.compareTo(arg2) != 0;
		}
	},
	GREATER {
		@Override
		public <T extends Comparable<T>> boolean isSatisfiedBy(T arg1, T arg2) {
			return arg1.compareTo(arg2) > 0;
		}
	},
	GREATER_EQ {
		@Override
		public <T extends Comparable<T>> boolean isSatisfiedBy(T arg1, T arg2) {
			return arg1.compareTo(arg2) >= 0;
		}
	},
	LOWER {
		@Override
		public <T extends Comparable<T>> boolean isSatisfiedBy(T arg1, T arg2) {
			return arg1.compareTo(arg2) < 0;
		}
	},
	LOWER_EQ {
		@Override
		public <T extends Comparable<T>> boolean isSatisfiedBy(T arg1, T arg2) {
			return arg1.compareTo(arg2) <= 0;
		}
	};
	
	public abstract <T extends Comparable<T>> boolean isSatisfiedBy(T arg1, T arg2);
}
