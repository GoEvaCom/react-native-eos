
Pod::Spec.new do |s|
  s.name         = "RNEosEcc"
  s.version      = "1.0.0"
  s.summary      = "RNEosEcc"
  s.description  = <<-DESC
                  RNEosEcc
                   DESC
  s.homepage     = "https://github.com/EvaCoop/react-native-eos"
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "raphael.gaudreault@eva.coop" }
  s.platform     = :ios, "8.0"
  s.source       = { :path => "ios" }
  s.source_files  = "ios/**/*.{h,m,swift,inc,table,c}"
  s.exclude_files = "ios/Libraries/include/asm_arm.inc.h"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  