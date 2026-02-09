package com.example.flightbooking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Flight Data Model
data class FlightInfo(
    val flightNumber: String,
    val airline: String,
    val departureAirport: String,
    val departureTime: String,
    val arrivalAirport: String,
    val arrivalTime: String,
    val duration: String,
    val price: String,
    val date: String
)

// Main Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlightBookingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FlightBookingApp()
                }
            }
        }
    }
}

// Theme
@Composable
fun FlightBookingTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF3B82F6),
            secondary = Color(0xFF1E3A8A),
            background = Color(0xFFF8FAFC)
        ),
        content = content
    )
}

// Main App Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightBookingApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Search", "My Bookings", "Profile")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E3A8A),
                        Color(0xFF3B82F6),
                        Color(0xFF60A5FA)
                    )
                )
            )
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Flight,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Flight Booking",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
            actions = {
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White
                    )
                }
            }
        )
        
        // Main Content
        when (selectedTab) {
            0 -> FlightSearchScreen()
            1 -> MyBookingsScreen()
            2 -> ProfileScreen()
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Bottom Navigation
        NavigationBar(
            containerColor = Color.White.copy(alpha = 0.95f),
            modifier = Modifier.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
        ) {
            tabs.forEachIndexed { index, title ->
                NavigationBarItem(
                    icon = {
                        when (index) {
                            0 -> Icon(Icons.Default.Search, contentDescription = null)
                            1 -> Icon(Icons.Default.BookmarkBorder, contentDescription = null)
                            2 -> Icon(Icons.Default.Person, contentDescription = null)
                        }
                    },
                    label = { Text(title) },
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF3B82F6),
                        selectedTextColor = Color(0xFF3B82F6),
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
            }
        }
    }
}

// Flight Search Screen
@Composable
fun FlightSearchScreen() {
    // Sample Flight Data - Delhi to Mumbai
    val sampleFlights = listOf(
        FlightInfo(
            flightNumber = "AI 101",
            airline = "Air India",
            departureAirport = "DEL",
            departureTime = "06:00",
            arrivalAirport = "BOM",
            arrivalTime = "08:30",
            duration = "2h 30m",
            price = "₹8,500",
            date = "Today"
        ),
        FlightInfo(
            flightNumber = "6E 234",
            airline = "IndiGo",
            departureAirport = "DEL",
            departureTime = "09:15",
            arrivalAirport = "BOM",
            arrivalTime = "11:45",
            duration = "2h 30m",
            price = "₹7,200",
            date = "Today"
        ),
        FlightInfo(
            flightNumber = "SG 456",
            airline = "SpiceJet",
            departureAirport = "DEL",
            departureTime = "14:20",
            arrivalAirport = "BOM",
            arrivalTime = "16:50",
            duration = "2h 30m",
            price = "₹6,800",
            date = "Today"
        ),
        FlightInfo(
            flightNumber = "UK 789",
            airline = "Vistara",
            departureAirport = "DEL",
            departureTime = "18:45",
            arrivalAirport = "BOM",
            arrivalTime = "21:15",
            duration = "2h 30m",
            price = "₹9,200",
            date = "Today"
        ),
        FlightInfo(
            flightNumber = "AI 205",
            airline = "Air India",
            departureAirport = "DEL",
            departureTime = "21:30",
            arrivalAirport = "BOM",
            arrivalTime = "00:05+1",
            duration = "2h 35m",
            price = "₹7,800",
            date = "Today"
        )
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SearchHeader()
        }
        
        item {
            Text(
                "Available Flights (${sampleFlights.size})",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        items(sampleFlights) { flight ->
            FlightCard(flight = flight)
        }
    }
}

// Search Header Component
@Composable
fun SearchHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                "Find Your Perfect Flight ✈️",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E3A8A)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("From", fontSize = 14.sp, color = Color.Gray)
                    Text("Delhi (DEL)", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                
                Icon(
                    Icons.Default.SwapHoriz,
                    contentDescription = "Swap",
                    tint = Color(0xFF3B82F6),
                    modifier = Modifier.size(24.dp)
                )
                
                Column {
                    Text("To", fontSize = 14.sp, color = Color.Gray)
                    Text("Mumbai (BOM)", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("June 12, 2025", fontSize = 16.sp, color = Color(0xFF3B82F6))
                Text("1 Passenger • Economy", fontSize = 16.sp, color = Color(0xFF3B82F6))
            }
        }
    }
}

// Flight Card Component
@Composable
fun FlightCard(flight: FlightInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Airline and Flight Number Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Flight,
                        contentDescription = null,
                        tint = Color(0xFF3B82F6),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            flight.airline,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1E3A8A)
                        )
                        Text(
                            flight.flightNumber,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        flight.price,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF059669)
                    )
                    Text(
                        "per person",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Flight Time Information
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Departure Info
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        flight.departureTime,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        flight.departureAirport,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Text(
                        "New Delhi",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                // Duration and Route Visualization
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        flight.duration,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.width(120.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color(0xFF3B82F6), shape = RoundedCornerShape(4.dp))
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(2.dp)
                                .background(Color(0xFF3B82F6))
                        )
                        Icon(
                            Icons.Default.FlightLand,
                            contentDescription = null,
                            tint = Color(0xFF3B82F6),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Non-stop",
                        fontSize = 12.sp,
                        color = Color(0xFF059669),
                        fontWeight = FontWeight.Medium
                    )
                }
                
                // Arrival Info
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        flight.arrivalTime,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        flight.arrivalAirport,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Text(
                        "Mumbai",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Additional Flight Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Luggage,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Baggage included",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.EventSeat,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Economy class",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Book Now Button
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3B82F6)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.BookOnline,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Book Now",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

// My Bookings Screen
@Composable
fun MyBookingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.BookmarkBorder,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "No bookings yet",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            "Your flight bookings will appear here",
            fontSize = 16.sp,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF3B82F6)
            )
        ) {
            Text("Search Flights", fontWeight = FontWeight.SemiBold)
        }
    }
}

// Profile Screen
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Person,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Welcome, Shri Harivansh!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
