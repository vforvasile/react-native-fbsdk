require 'json'
package = JSON.parse(File.read(File.join(__dir__, './', 'package.json')))

Pod::Spec.new do |s|
  s.name          = package['name']
  s.version       = package['version']
  s.summary       = package['description']
  s.requires_arc  = true
  s.author        = { 'dzhuowen' => 'dzhuowen@fb.com' }
  s.license       = package['license']
  s.homepage      = package['homepage']
  s.source        = { :git => 'https://github.com/facebook/react-native-fbsdk.git', :tag => "v#{package['version']}" }
  s.platform      = :ios, '8.0'
  s.dependency      'React'

  s.subspec 'Core' do |ss|
    ss.dependency     'FBSDKCoreKit', '= 5.13.1'
    ss.source_files = 'ios/RCTFBSDK/core/*.{h,m}'
  end

  s.subspec 'Login' do |ss|
    ss.dependency     'FBSDKLoginKit', '= 5.13.1'
    ss.source_files = 'ios/RCTFBSDK/login/*.{h,m}'
  end
end
