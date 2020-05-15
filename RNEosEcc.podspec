
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
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/author/RNEosEcc.git", :tag => "master" }
  s.source_files  = "RNEosEcc/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  