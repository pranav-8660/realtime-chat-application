# Real-Time Chat Application

A Spring Boot-based chat application with real-time messaging using WebSockets and MySQL.

---

## Functionalities

✅ **User Authentication**  
- Login  
- Registration  

✅ **Contacts Management**  
- Users can chat **only** with stored contacts  

✅ **Chat Initialization**  
- Empty chat history for new users  
- "New Chat" button displays stored contacts to start a conversation  

✅ **Chat History Management**  
- Conversations stored in the database  
- Resume chats with existing history  

✅ **Chat Deletion**  
- Users can permanently delete chats  

---

## Tech Stack

- **Backend**: Spring Boot  
- **Real-Time Communication**: WebSockets  
- **Message Processing (Optional)**: Apache Kafka  
- **Database**: MySQL  

---

## Development Plan

1. **User Authentication & Contact Management**  
   - Spring Security for login/registration  
   - Store users and contacts in MySQL  

2. **Chat Functionality**  
   - WebSockets for real-time messaging  
   - Save messages in MySQL  
   - Optional Kafka integration for message queuing  

3. **Chat UI & Management**  
   - APIs for:  
     - Fetching contacts and chat history  
     - Starting new chats  
     - Deleting chats  
