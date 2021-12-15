@file:OptIn(ExperimentalComposeWebWidgetsApi::class)
package pl.mareklangiewicz.kthreelhu

import androidx.compose.runtime.*
import pl.mareklangiewicz.widgets.kim.Kim.Companion.toggle
import pl.mareklangiewicz.widgets.kim.Kim.Companion.trigger
import pl.mareklangiewicz.widgets.kim.Kim.Companion.cmdMouseMove
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.common.material.Text
import org.jetbrains.compose.common.ui.ExperimentalComposeWebWidgetsApi
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.renderComposable
import org.jetbrains.compose.web.ui.Styles
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.MouseEvent
import pl.mareklangiewicz.widgets.CmnDText
import pl.mareklangiewicz.widgets.kim.GamepadAddEffect
import pl.mareklangiewicz.widgets.kim.GamepadRemEffect
import pl.mareklangiewicz.widgets.kim.KeyDownEffect
import pl.mareklangiewicz.widgets.kim.Kim
import pl.mareklangiewicz.widgets.kim.MouseMoveEffect
import pl.mareklangiewicz.widgets.kim.MouseWheelEffect

fun main() {
    console.log("Kotlin version: ${KotlinVersion.CURRENT}")
    val root = document.getElementById("root") as HTMLElement
    renderComposable(root = root) { AppJs() }
}

@Composable fun AppJs() {
    Style(Styles)
    Kim.Area {
        Kim.KeyDownEffect(window)
        Kim.MouseMoveEffect(window)
        Kim.MouseWheelEffect(window)
        Kim.GamepadAddEffect(window)
        Kim.GamepadRemEffect(window)
        'q' trigger { window.close() }
        Kim.Frame { AppContent() }
    }
}

@Composable private fun AppContent() {
    val scope = rememberCoroutineScope()
    var camPos by remember { mutableStateOf(XYZ(0.0, 0.0, 20.0)) }
    var camRot by remember { mutableStateOf(XYZ(0.0, 0.0, 0.0)) }

    '3' trigger { scope.launch { threeExperiment3() } }
    '4' trigger { scope.launch { threeExperiment4() } }

    val te by 'e'.toggle(false)
    val tr by 'r'.toggle(false)
    val tz by 'z'.toggle(false)
    val ts by 's'.toggle(false)

    lateinit var mousePosBackup: XY
    lateinit var camPosBackup: XYZ
    lateinit var camRotBackup: XYZ
    var moving = false
    'c' trigger { if (moving) { camPosBackup = camPos; camRotBackup = camRot } }
    cmdMouseMove {
        val mousePos = it.x xy it.y
        if (!moving) mousePosBackup = mousePos
        val mousePosDelta = mousePos - mousePosBackup
        val factor = if (ts) 0.003 else 0.1
        when {
            !moving && (te || tr) -> {
                mousePosBackup = mousePos
                camPosBackup = camPos
                camRotBackup = camRot
                moving = true
            }
            te -> camPos = camPosBackup + mousePosDelta.toXYZ(swapYZ = tz) * factor
            tr -> camRot = camRotBackup + mousePosDelta.toXYZ(swapYZ = tz) * factor / 10.0
            moving -> { camPos = camPosBackup; camRot = camRotBackup; moving = false }
        }
    }


    H2 { Text("Kthreelhu JS") }
    Div {
        CmnDText("camPos:$camPos; camRot:$camRot; ts:$ts; tq:$te; tw:$tr tz:$tz", mono = true)
        KthExamples(camPos, camRot)
    }
}

fun XY.toXYZ(z: Double = 0.0, swapYZ: Boolean = false) = if (swapYZ) x xy z yz y else x xy y yz z

fun threeExperiment3() {
    console.log("TODO")
    window.alert("TODO")
}

suspend fun threeExperiment4() {
    console.log("TODO")
    window.alert("TODO")
//    val system = parseJsonBraxSystem("./walking_ant.json")
//    model?.scene?.addBraxSystem(system)
//    // TODO: trajectory/animation stuff (based on brax repo)
//    // TODO: review all brax stuff after something is moving..
}
