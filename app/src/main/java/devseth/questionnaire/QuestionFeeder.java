package devseth.questionnaire;

/**
 * Created by Dev Seth on 6/28/2017.
 */

public class QuestionFeeder {

    private String q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14,
            q15, q16, q17, q18, q19, q20, q21, q22, q23, q24;                   //the questions
    private String[] questions;                                                 //array storing questions
    private int questionIndex;                                                  //current position in array
    private int language;                                                       //*beep* for Hindi, press 0 *beep* For English, press 1

    public QuestionFeeder() {
        questionIndex = -1;
    }

    public String nextQuestion() {

        questionIndex++;                                        //goto next question

        if (questionIndex >= questions.length)                    //have we reached the last question?
            return "Survey Complete!";                                         //if yes, return end

        return questions[questionIndex];                        //or else return the next question
    }


    public void setLanguage(int i) {

        if (i == 0)   //Hindi
        {
            q1 = "क्या आप मुँह के कैंसर के बारे मे जानते है?";
            q2 = "क्या आप ऐसे किसी व्यक्ति को जानते है जिसे मुँह का कैंसर हुआ है?";
            q3 = "क्या आप जानते है मुँह का कैंसर काय से होता है?";
            q4 = "क्या मुँह का कैंसर छूत की बीमारी है?";
            q5 = "क्या बीड़ी/सिगरेट पीने से कैंसर होता है?";
            q6 = "क्या शराब पीने से मुँह का कैंसर होता है?";
            q7 = "क्या पान चबाने से मुँह का कैंसर होता है?";
            q8 = "क्या खराब दाँत या मसूड़ो से मुँह का कैंसर होता है?";
            q9 = "क्या मुँह के कैंसर का उम्र से कोई संबंध है?";

            q10 = "क्या आप बीड़ी/सिगरेट पीते है?";
            q11 = "क्या आप पान चबाते है?";
            q12 = "क्या आप पान मसाला खाते है?";
            q13 = "क्या आप शराब का सेवन करते है?";
            q14 = "क्या आपके परिवार का कोई सदस्य तम्बाकू का किसी भी स्वरूप में उपयोग करता है?";

            q15 = "क्या आपने ने अपने मुँह को अंदर से देखा है?";
            q16 = "क्या पूरा बढ़ा मुँह खोलने में कोई तकलीफ होती है?";
            q17 = "क्या आपको सामान्य खाना खाने में तीखा लगता है?";
            q18 = "क्या आपको मुँह में कोई लाल या सफ़ेद चट्टा, छाला है?";
            q19 = "क्या आपको मुँह में कोई नुकीला या धार वाला दाँत है जो चुबता हो?";
            q20 = "क्या आपको मुँह में कोई छला है जो तीन हफ्ते से ज़्यादा हो गए और भरा या ठीक न हुआ हो?";
            q21 = "क्या पिछले दो-तीन हफ़्तों में आप की आवाज़ में कोई बदलाव हुआ है जो ठीक नहीं हो रहा है?";
            q22 = "क्या आपके कान में दर्द बना रहता है?";
            q23 = "क्या आपको खाना निगलने में कोई तकलीफ होती है?";
            q24 = "क्या आपको गले या गर्दन में कोई सूजन या गठान महसूस होती है?";
        } else if (i == 1) //English
        {
            q1 = "Have you heard about oral cancer?";
            q2 = "Do you know anyone who had mouth cancer?";
            q3 = "DO you know the cause of oral cancer?";
            q4 = "Is oral cancer a contagious disease?";
            q5 = "Can smoking cause oral cancer?";
            q6 = "Can alcohol cause oral cancer?";
            q7 = "Does chewing paan (betel leaves) cause oral cancer?";
            q8 = "Is poor oral health a cause of oral cancer?";
            q9 = "Is oral cancer related to age?";

            q10 = "Do you smoke cigarettes or bidi?";
            q11 = "DO you chew paan with tobacco?";
            q12 = "Do you eat paan masala?";
            q13 = "Do you consume alcohol?";
            q14 = "Do any of your family members smoke or chew tobacco?";

            q15 = "Have you ever examined your oral cavity?";
            q16 = "Do you experience difficulties in opening your mouth fully?";
            q17 = "Do you have a burning sensation on eating normal food?";
            q18 = "Do you have any red or white patches in your oral cavity?";
            q19 = "Do you have any sharp tooth that hurt your cheek?";
            q20 = "Do you have any ulcer that has not healed for more than 3 weeks?";
            q21 = "Have you noticed a change in your voice in the last 2-3 weeks or before that?";
            q22 = "Do you continuously suffer from earache?";
            q23 = "Do you have difficulties in swallowing food?";
            q24 = "Have you noticed any swelling in your neck?";


        }

        questions = new String[]{q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14,
                q15, q16, q17, q18, q19, q20, q21, q22, q23, q24};


    }
}
