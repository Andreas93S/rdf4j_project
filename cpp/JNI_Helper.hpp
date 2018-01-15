#ifndef JNI_HELPER_H
#define JNI_HELPER_H

#include <jni.h>
#include <iostream>
#include <stdexcept>

class JNI_Helper {
	private:
		JNIEnv* env;
		JavaVM* jvm;
	public:
		JNI_Helper(std::string jar_path);
		~JNI_Helper();

		/*
			Get a class from a program in the JVM
			@param class_name The name of the class
			@return The jclass object
		*/
		jclass 
		get_class(std::string class_name);

		/*
			Get a method ID from a class in the JVM
			@param class_j The jclass object to find the method in
			@param method_name The name of the method
			@param signature The method signature
			@return The jmethodID object
		*/
		jmethodID
		get_static_mid(jclass class_j, std::string method_name, std::string signature);

		/*
			Calls a static void method in a class in the JVM
			@param class_j The class where the method is
			@param mid The method ID

			TODO: Add support for arguments
		*/
		void
		call_static_void_method(jclass class_j, jmethodID mid);
};

#endif
