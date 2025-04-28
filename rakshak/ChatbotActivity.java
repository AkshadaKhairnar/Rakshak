package com.example.rakshak;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ChatbotActivity extends AppCompatActivity {

    EditText userInput;
    Button sendButton;
    TextView chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        userInput = findViewById(R.id.userInput);
        sendButton = findViewById(R.id.sendButton);
        chatHistory = findViewById(R.id.chatHistory);

        sendButton.setOnClickListener(view -> {
            String input = userInput.getText().toString().trim();
            if (!input.isEmpty()) {
                chatHistory.append("\n\n👤 You: " + input);
                chatHistory.append("\n🤖 Bot: " + getBotResponse(input));
                userInput.setText("");
            }
        });
    }

    private String getBotResponse(String input) {
        input = input.toLowerCase();
        if (input.contains("first aid")) return "First aid can save lives. Clean the wound to prevent infection, apply pressure to stop bleeding, and seek medical attention if needed. It's also important to know CPR techniques for emergencies.";
        else if (input.contains("accident")) return "In case of an accident, call 112 immediately. Ensure the scene is safe, provide first aid if necessary, and wait for emergency services. Don't forget to collect evidence if possible.";
        else if (input.contains("helmet")) return "Helmets are a life-saving piece of safety gear. Always wear one while riding a bike or motorcycle. It reduces the risk of fatal head injuries by up to 40%!";
        else if (input.contains("seatbelt")) return "Seatbelts are vital for your safety in a car. They prevent serious injuries and fatalities in the event of a crash. Always wear your seatbelt, no matter how short the journey.";
        else if (input.contains("emergency number")) return "In India, 112 is the all-in-one emergency number for police, ambulance, and fire services. Save it in your phone to access help instantly during an emergency.";
        else if (input.contains("speed")) return "Speeding is one of the leading causes of accidents. Adhering to speed limits helps maintain control of your vehicle and gives you time to react in case of sudden road hazards.";
        else if (input.contains("road safety")) return "Road safety tips include obeying traffic signs, staying alert, and driving cautiously. Always watch out for pedestrians and cyclists, especially in busy areas.";
        else if (input.contains("drunk driving")) return "Drunk driving is extremely dangerous. Alcohol impairs judgment and slows reaction time. Always have a designated driver or use a cab/rideshare service if you're drinking.";
        else if (input.contains("weather conditions")) return "Weather can dramatically affect driving conditions. During rain, fog, or snow, reduce speed, turn on headlights, and maintain a safe distance from other vehicles.";
        else if (input.contains("pedestrian safety")) return "Pedestrian safety is critical. Always use crosswalks, obey traffic signals, and avoid distractions like phones while walking on the road.";
        else if (input.contains("vehicle maintenance")) return "Regular vehicle maintenance is important for road safety. Make sure your tires, brakes, lights, and fluids are checked periodically to avoid breakdowns or accidents.";
        else if (input.contains("distracted driving")) return "Distracted driving is one of the most common causes of accidents. Avoid texting, eating, or using your phone while driving. Stay focused on the road!";
        else if (input.contains("blind spots")) return "Blind spots are areas around your vehicle that you can't see through mirrors. Always check your blind spots before changing lanes or merging into traffic.";
        else if (input.contains("nashik accident spots")) return "In Nashik, the most common accident-prone areas are the Gangapur Road, Mumbai Agra Road, and roads near Saptashrungi temple. Always drive cautiously in these areas.";
        else if (input.contains("nashik police station")) return "The Nashik City Police Station is located at Nashik Road, opposite the Railway Station. In case of emergencies, contact them at 0253-2460101.";
        else if (input.contains("nashik hospital")) return "For medical emergencies in Nashik, you can visit the Suyash Hospital or the Nashik Civil Hospital. Both provide 24/7 emergency services.";
        else if (input.contains("nashik traffic tips")) return "In Nashik, traffic can get heavy near Panchavati and Nashik Road. Try to avoid peak hours for smoother travel. Always use seatbelts and helmets, and follow traffic rules.";
        else if (input.contains("hi") || input.contains("hello")) return "Hello! I'm your safety assistant. How can I assist you today? Feel free to ask me anything about road safety or emergency procedures in Nashik.";
        else return "Sorry, I didn’t understand. Please ask about road safety, first aid, emergency contacts, or Nashik-related queries.";
    }

}