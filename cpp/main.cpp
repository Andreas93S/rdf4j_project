#include <iostream>
#include <stdexcept>

#include "../cpp_java/JNI_Helper.hpp"

int main(int argc, char** argv) {
	JNI_Helper jh("./maven_test/rdf4j_connector/target/rdf4j_connector-1.0-SNAPSHOT.jar");
	jclass main_class = jh.get_class("Connector");
	//jmethodID main_mid = jh.get_static_mid(main_class, "main", "([Ljava/lang/String;)V");
	jmethodID initialize_mid = jh.get_static_mid(main_class, "initialize", "(Ljava/lang/String;Ljava/lang/String;)V");
	jmethodID main_mid = jh.get_static_mid(main_class, "getScore", "(IILjava/lang/String;)D");

	jstring serverID = jh.create_string("http://vm25.cs.lth.se/rdf4j-server");
	jstring repoID = jh.create_string("object_repo");
	jh.call_static_void_method_args(main_class, initialize_mid, serverID, repoID);

	jint scene_index = 0;
	jint cluster_index = 0;
	jstring model_name = jh.create_string("Wood_object");
	jdouble score = jh.call_static_double_method_args(main_class, main_mid, scene_index, cluster_index, model_name);
	std::cout << score << std::endl;
	return 0;

	/*
	std::string maven_jar_path = "-Djava.class.path=./maven_test/my-app/target/my-app-1.0-SNAPSHOT-jar-with-dependencies.jar";

	const int kNumOptions = 1;
	JavaVMOption options[kNumOptions] = {
		{ const_cast<char*>(maven_jar_path.c_str()), NULL }
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
