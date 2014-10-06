package com.myschool.user.constants;

/**
 * The Enum SecurityQuestion.
 */
public enum SecurityQuestion {

    /** The DATE_OF_BIRTH. */
    DATE_OF_BIRTH {
        @Override
        public String getQuestion() {
            return "What is Your Date Of Birth (DD/MM/YYYY)?";
        }
    },
    
    /** The DATE_OF_JOINING. */
    DATE_OF_JOINING {
        @Override
        public String getQuestion() {
            return "What is Your Date Of Joining (DD/MM/YYYY)?";
        }
        
    };

    /**
     * Gets the question.
     *
     * @return the question
     */
    public abstract String getQuestion();
}
