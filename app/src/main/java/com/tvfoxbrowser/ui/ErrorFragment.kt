package com.tvfoxbrowser.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tvfoxbrowser.CrashHandler
import com.tvfoxbrowser.MainActivity
import com.tvfoxbrowser.R
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.databinding.FragmentErrorBinding

/**
 * 错误页。
 * - GeckoRuntime 初始化失败时显示(让用户知道为什么浏览器不能用)
 * - 上次崩溃后启动时显示(从 CrashHandler 读取日志)
 *
 * 不依赖 GeckoView,确保即使内核完全不可用也能显示。
 */
class ErrorFragment : Fragment() {

    private var _binding: FragmentErrorBinding? = null
    private val binding get() = _binding!!

    /** 错误模式:内核初始化失败 / Java 崩溃 / native SIGSEGV 崩溃 */
    enum class Mode { RUNTIME_INIT, CRASH, NATIVE_CRASH }
    private var mode: Mode = Mode.RUNTIME_INIT
    // 缓存崩溃日志,清除磁盘后仍可在弹窗中查看
    private var cachedCrashLog: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mode = (savedInstanceState?.getSerializable(ARG_MODE) as? Mode)
            ?: (arguments?.getSerializable(ARG_MODE) as? Mode)
            ?: Mode.RUNTIME_INIT
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (mode) {
            Mode.RUNTIME_INIT -> {
                binding.errorTitle.setText(R.string.error_runtime_title)
                binding.errorMessage.setText(R.string.error_runtime_message)
                val err = TvFoxApp.getRuntimeError()
                binding.errorDetail.text = err?.let {
                    "${it.javaClass.name}: ${it.message ?: "(no message)"}\n" +
                        it.stackTrace.take(5).joinToString("\n") { "    at $it" }
                } ?: ""
            }
            Mode.CRASH -> {
                binding.errorTitle.setText(R.string.error_crash_title)
                binding.errorMessage.setText(R.string.error_crash_message)
                // 先缓存完整日志(供弹窗查看),再清除磁盘日志,
                // 避免用户关闭后再打开仍卡在崩溃页(死循环)。
                cachedCrashLog = CrashHandler.readLog()
                CrashHandler.clearLog()
                binding.errorDetail.text = cachedCrashLog
                    ?.lines()?.takeLast(20)?.joinToString("\n")
                    ?: getString(R.string.error_log_empty)
            }
            Mode.NATIVE_CRASH -> {
                // native SIGSEGV 在 Java 层抓不到,crash.log 通常是空的。
                // 这种崩溃最常见的原因是 GPU/WebRender 在国产 TV 驱动上崩。
                binding.errorTitle.setText(R.string.error_native_title)
                binding.errorMessage.setText(R.string.error_native_message)
                binding.errorDetail.text = "无 Java 堆栈(native SIGSEGV)"
                // 重置启动追踪状态,让用户重启后能再次尝试
                com.tvfoxbrowser.StartupTracker.reset()
            }
        }

        binding.btnViewLog.setOnClickListener {
            showLogDialog()
        }
        binding.btnViewLog.requestFocus()
        binding.btnRestart.setOnClickListener {
            restartApp()
        }
    }

    private fun showLogDialog() {
        val log = cachedCrashLog
            ?: CrashHandler.readLog()
            ?: getString(R.string.error_log_empty)
        AlertDialog.Builder(requireContext(), android.R.style.Theme_DeviceDefault_Dialog_Alert)
            .setTitle(R.string.error_log_title)
            .setMessage(log)
            .setPositiveButton(R.string.error_close, null)
            .show()
    }

    private fun restartApp() {
        // 清除崩溃日志,避免下次启动再次进入错误页
        CrashHandler.clearLog()
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_MODE = "mode"
        fun newInstance(mode: Mode): ErrorFragment {
            return ErrorFragment().apply {
                arguments = Bundle().apply { putSerializable(ARG_MODE, mode) }
            }
        }
    }
}
