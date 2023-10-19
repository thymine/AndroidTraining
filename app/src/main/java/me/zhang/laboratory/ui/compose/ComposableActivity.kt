package me.zhang.laboratory.ui.compose

import SampleData.conversationSample
import SampleData.emptySample
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.zhang.laboratory.R

class ComposableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DarkLightMaterialTheme {
                Conversation(messages = conversationSample)
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    if (messages.isEmpty()) {
        Text(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            text = "No message.",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontFamily = FontFamily.Cursive,
            color = MaterialTheme.colorScheme.secondary,
        )
    } else {
        LazyColumn {
            items(messages) { msg ->
                MessageCard(msg)
            }
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.scenery),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
            )

            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))

            // We keep track if the message is expanded or not in this variable
//            var isExpanded by rememberSaveable { mutableStateOf(false) }

            // surfaceColor will be updated gradually from one color to the other
            val surfaceColor by animateColorAsState(
                if (msg.isExpand()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                label = "surfaceColor"
            )

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                // We toggle the isExpanded variable when we click on this Text
                Text(
                    text = msg.body,
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .clickable { msg.toggle() },
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (msg.isExpand()) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@PreviewDarkLight
@Composable
fun PreviewMessageCard() {
    DarkLightMaterialTheme {
        Conversation(messages = conversationSample)
    }
}

@PreviewDarkLight
@Composable
fun PreviewEmptyMessageCard() {
    DarkLightMaterialTheme {
        Conversation(messages = emptySample)
    }
}