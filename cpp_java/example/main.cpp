#include <iostream>
#include <stdexcept>
#include "../JNI_Helper.hpp"

int main(int argc, char** argv) {
	JNI_Helper jh("./");
	jclass main_class = jh.get_class("Main");
	jmethodID invert_mid = jh.get_static_mid(main_class, "invert", "(Z)Z");

	jboolean val = true;
	val = jh.call_static_boolean_method(main_class, invert_mid, val);
	std::string op = val ? "TRUE" : "False";
	std::cout << op << std::endl;


	/*
	const int kNumOptions = 1;
	JavaVMOption options[kNumOptions] = {
		{ const_cast<char*>("-Djava.class.path=./my-app.jar"), NULL }
	};

	JavaVMInitArgs vm_args;
	vm_args.version = JNI_VERSION_1_6;
	vm_args.nOptions = kNumOptions;
	vm_args.options = options;

	JNIEnv* env;
	JavaVM* jvm;
	int res = JNI_CreateJavaVM(&jvm, reinterpret_cast<void**>(&env), &vm_args);
	if (res != JNI_OK) {
		std::cerr << "FAILED: JNI_CreateJavaVM " << res << std::endl;
		return -1;
	}

	jclass main_class = get_class(env, "rdf4j_get_statements_from_server");
	jmethodID main_mid = get_static_mid(env, main_class, "main", "([Ljava/lang/String;)V");
	env->CallStaticVoidMethod(main_class, main_mid, NULL);
	*/
	
	/*
	jclass main_class = get_class(env, "Main");
	jmethodID main_mid = get_static_mid(env, main_class, "main", "([Ljava/lang/String;)V");

	// Build argument array
	const jsize kNumArgs = 2;
	jclass string_class = env->FindClass("java/lang/String");
	jobject initial_element = NULL;
	jobjectArray method_args = env->NewObjectArray(kNumArgs, string_class, initial_element);
	jstring method_args_0 = env->NewStringUTF("Hello1, Java!");
	jstring method_args_1 = env->NewStringUTF("Hello2, Java!");
	env->SetObjectArrayElement(method_args, 0, method_args_0);
	env->SetObjectArrayElement(method_args, 1, method_args_1);

	// Call main() with above argument array
	env->CallStaticVoidMethod(main_class, main_mid, method_args);

	jmethodID invert_mid = get_static_mid(env, main_class, "invert", "(Z)Z");
	jboolean b = true;
	b = env->CallStaticBooleanMethod(main_class, invert_mid, b);
	std::string val = b ? "TRUE" : "False";
	std::cout << val << std::endl;
	*/

	/*
	jvm->DestroyJavaVM();
	return 0;
	*/
}
